package org.sjasinski.ws.filters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static Long minTime = null;
    private static Long maxTime = null;
    private static Long startTime = null;

    private static final Multiset<Long> REQUEST_PROCESSING_TIMES = TreeMultiset.create();

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        startTime = System.currentTimeMillis();

        LOGGER.info("Resource: /{} ", requestContext.getUriInfo().getPath());
        LOGGER.info("Method: /{} ", requestContext.getMethod());

        String entity = readEntityStream(requestContext);
        if(StringUtils.isNotBlank(entity)) {
            LOGGER.info("Entity: {}", entity);
        }
    }

    private String readEntityStream(ContainerRequestContext requestContext)
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        final InputStream inputStream = requestContext.getEntityStream();
        final StringBuilder builder = new StringBuilder();
        try
        {
            ReaderWriter.writeTo(inputStream, outStream);
            byte[] requestEntity = outStream.toByteArray();
            if (requestEntity.length == 0) {
                builder.append("");
            } else {
                builder.append(new String(requestEntity));
            }
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity) );
        } catch (IOException e) {
            LOGGER.info("Exception occurred while reading entity stream: {}", e.getMessage());
        }
        return builder.toString();
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        if (startTime != null) {
            Long executionTime = System.currentTimeMillis() - startTime;
            REQUEST_PROCESSING_TIMES.add(executionTime);
            LOGGER.info("Current request execution time: {} milliseconds", executionTime);

            determineMinExecutionTime(executionTime);
            LOGGER.info("Minimum request execution time: {} milliseconds", minTime);

            determineMaxExecutionTime(executionTime);
            LOGGER.info("Maximum request execution time: {} milliseconds", maxTime);

            LOGGER.info("Quantile 95: {} milliseconds", getQuantile(95));

            startTime = null;
        }
    }

    private void determineMinExecutionTime(Long executionTime) {
        if(minTime != null) {
            if (executionTime < minTime) {
                minTime = executionTime;
            }
        } else {
            minTime = executionTime;
        }
    }

    private void determineMaxExecutionTime(Long executionTime) {
        if(maxTime != null) {
            if (executionTime > maxTime) {
                maxTime = executionTime;
            }
        } else {
            maxTime = executionTime;
        }
    }

    private Long getQuantile(int q) {
        int index = (int) Math.round(REQUEST_PROCESSING_TIMES.size() * (q / 100.0)) - 1;
        LOGGER.info("Request execution time set: {}, size: {}", REQUEST_PROCESSING_TIMES, REQUEST_PROCESSING_TIMES.size());
        LOGGER.info("Quantile 95 index: {}", index);

        if (index < REQUEST_PROCESSING_TIMES.size()) {
            return (Long) REQUEST_PROCESSING_TIMES.toArray()[index];
        }
        return null;
    }
}