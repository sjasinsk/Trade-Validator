$(document).ready(function() {

  function ajaxCallRequest(f_method, f_url, f_data) {
    $("#dataSent").val(unescape(f_data));
    $.ajax({
      url: f_url,
      type: f_method,
	  data: f_data,
      contentType: 'application/json',
      dataType: 'json',
      success: function(data) {
        var jsonResult = JSON.stringify(data, replacer);
        $("#results").val(unescape(jsonResult));
      },
      error: function(data) {
        var jsonResult = JSON.stringify(data);
        $("#results").val(unescape(jsonResult));
      }
    });
  }
  
  $("#sendJson").click(function(event) {
    event.preventDefault();
    var form = $('#ajaxForm');
    var method = form.attr('method');
    var url = 'http://localhost:8080' + form.attr('action');
    var jsonData = {};
	$("#results").val('Waiting for response');
    $.each($(form).serializeArray(), function() {
      jsonData[this.name] = this.value;
    });
    var data = JSON.stringify(jsonData, replacer);
    console.log(data);
    ajaxCallRequest(method, url, data);
  });

  
  $("#defaultSpotData").click(function(event) {
    event.preventDefault();
    $('#customer').val('PLUTO1');
    $('#ccyPair').val('EURUSD');
    $('#type').val('Spot');
    $('#direction').val('BUY');
    $('#tradeDate').val('2016-08-11');
    $('#amount1').val(1000000.00);
    $('#amount2').val(1120000.00);
    $('#rate').val(1.12);
    $('#valueDate').val('2016-08-15');
    $('#legalEntity').val('CS Zurich');
    $('#trader').val('Johann Baumfiddler');

    $('#type').selectpicker('refresh');
    toggleFields();
  });

  $("#defaultForwardData").click(function(event) {
    event.preventDefault();
    $('#customer').val('PLUTO2');
    $('#ccyPair').val('EURUSD');
    $('#type').val('Forward');
    $('#direction').val('SELL');
    $('#tradeDate').val('2016-08-11');
    $('#amount1').val(1000000.00);
    $('#amount2').val(1120000.00);
    $('#rate').val(1.12);
    $('#valueDate').val('2016-08-22');
    $('#legalEntity').val('CS Zurich');
    $('#trader').val('Johann Baumfiddler');

    $('#type').selectpicker('refresh');
    toggleFields();
  });

  $("#defaultOptionEuData").click(function(event) {
    event.preventDefault();
    $('#customer').val('PLUTO1');
    $('#ccyPair').val('EURUSD');
    $('#type').val('VanillaOption');
    $('#style').val('EUROPEAN');
    $('#direction').val('SELL');
    $('#strategy').val('CALL');
    $('#tradeDate').val('2016-08-11');
    $('#amount1').val(1000000.00);
    $('#amount2').val(1120000.00);
    $('#rate').val(1.12);
    $('#deliveryDate').val('2016-08-22');
    $('#expiryDate').val('2016-08-19');
    $('#excerciseStartDate').val();
    $('#payCcy').val('USD');
    $('#premium').val(0.20);
    $('#premiumCcy').val('USD');
    $('#premiumType').val('%USD');
    $('#premiumDate').val('2016-08-12');
    $('#legalEntity').val('CS Zurich');
    $('#trader').val('Johann Baumfiddler');

    $('#type').selectpicker('refresh');
    toggleFields();
  });

  $("#defaultOptionUsData").click(function(event) {
      event.preventDefault();
      $('#customer').val('PLUTO1');
      $('#ccyPair').val('EURUSD');
      $('#type').val('VanillaOption');
      $('#style').val('AMERICAN');
      $('#direction').val('SELL');
      $('#strategy').val('CALL');
      $('#tradeDate').val('2016-08-11');
      $('#amount1').val(1000000.00);
      $('#amount2').val(1120000.00);
      $('#rate').val(1.12);
      $('#deliveryDate').val('2016-08-22');
      $('#expiryDate').val('2016-08-19');
      $('#excerciseStartDate').val('2016-08-12');
      $('#payCcy').val('USD');
      $('#premium').val(0.20);
      $('#premiumCcy').val('USD');
      $('#premiumType').val('%USD');
      $('#premiumDate').val('2016-08-12');
      $('#legalEntity').val('CS Zurich');
      $('#trader').val('Johann Baumfiddler');

      $('#type').selectpicker('refresh');
      toggleFields();
    });

   $("#type").change(function () {
        toggleFields();
   });

});

function replacer(key,value)
{
    if (value === null || value === '') return undefined
    return value
}

function toggleFields() {
    if ($('#type').val() === 'Spot'
        || $('#type').val() === 'Forward') {
        $('.option-exclusive').hide();
        $('.option-exclusive input').val('');
        $('.spot-forward-exclusive').show();
    } else {
        $('.spot-forward-exclusive').hide();
        $('.spot-forward-exclusive input').val('');
        $('.option-exclusive').show();
    }
}

