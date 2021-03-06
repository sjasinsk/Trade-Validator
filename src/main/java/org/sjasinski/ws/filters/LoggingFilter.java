package org.sjasinski.ws.filters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

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

/**
 * Logging filter for showing simple request processing statistical data.
 */
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    private final AtomicLong minTime = new AtomicLong(Long.MAX_VALUE);
    private final AtomicLong maxTime = new AtomicLong(Long.MIN_VALUE);
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final Multiset<Long> requestProcessingTimes = TreeMultiset.create();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        startTime.set(System.currentTimeMillis());

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
        if (startTime.get() != null) {
            Long processingTime = System.currentTimeMillis() - startTime.get();
            LOGGER.info("Current request processing time: {} milliseconds", processingTime);

            Long q95;
            synchronized (requestProcessingTimes) {
                requestProcessingTimes.add(processingTime);
                q95 = getQuantile(95);
            }

            determineMinExecutionTime(processingTime);
            LOGGER.info("Minimum request processing time: {} milliseconds", minTime);

            determineMaxExecutionTime(processingTime);
            LOGGER.info("Maximum request processing time: {} milliseconds", maxTime);

            if (q95 != null) {
                LOGGER.info("Quantile 95: {} milliseconds", q95);
            } else {
                LOGGER.info("Quantile 95: N/A");
            }

            // thread-local cleanup
            startTime.remove();
        }
    }

    private void determineMinExecutionTime(Long processingTime) {
        long expectedMin = minTime.get();

        while (processingTime < expectedMin && !minTime.compareAndSet(expectedMin, processingTime)) {
            expectedMin = minTime.get();
        }
    }

    private void determineMaxExecutionTime(Long processingTime) {
        long expectedMax = maxTime.get();

        while (processingTime > expectedMax && !maxTime.compareAndSet(expectedMax, processingTime)) {
            expectedMax = maxTime.get();
        }
    }

    private Long getQuantile(int q) {
        int index = (int) Math.round(requestProcessingTimes.size() * (q / 100.0)) - 1;
        LOGGER.info("Request processing time set: {}, size: {}", requestProcessingTimes, requestProcessingTimes.size());
        LOGGER.info("Quantile {} index: {}", q, index);

        if (index >= 0 && index < requestProcessingTimes.size()) {
            return (Long) requestProcessingTimes.toArray()[index];
        }
        return null;
    }
}