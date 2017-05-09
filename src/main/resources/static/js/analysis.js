/**
 * Created by ss on 2017/5/6.
 */
function drawComboChart(comboArgs) {

    var comboValue = comboArgs.argsData;

    for (var i = 1; i < comboValue.length; i++) {
        var comboItem = comboValue[i];
        for (var j = 1; j < comboItem.length; j++) {
            comboItem[j] = parseInt(comboItem[j]);
            comboValue[i] = comboItem;
        }
    }

    var data = google.visualization.arrayToDataTable(comboValue);

    var options = {
        title: comboArgs.title,
        vAxis: {title: comboArgs.vAxis},
        hAxis: {title: comboArgs.hAxis},
        seriesType: 'bars',
        series: {5: {type: 'line'}}
    };

    var chart = new google.visualization.ComboChart(document.getElementById('combo'));
    chart.draw(data, options);
}

function drawPieChart(pieArgs) {

    var pieValue = pieArgs.argsData;

    for (var i = 1; i < pieValue.length; i++) {
        var pieItem = pieValue[i];
        for (var j = 1; j < pieItem.length; j++) {
            pieItem[j] = parseInt(pieItem[j]);
            pieValue[i] = pieItem;
        }
    }

    var data = google.visualization.arrayToDataTable(pieValue);

    var options = {
        title: pieArgs.title,
        legend: 'none',
        pieSliceText: 'label',
        slices: {
            4: {offset: 0.2},
            12: {offset: 0.3},
            14: {offset: 0.4},
            15: {offset: 0.5}
        }
    };

    var chart = new google.visualization.PieChart(document.getElementById('pie'));
    chart.draw(data, options);
}

function drawSankeyChart(sankeyArgs) {

    var sankeyValue = sankeyArgs.argsData;

    for (var i = 0; i < sankeyValue.length; i++) {
        var sankeyItem = sankeyValue[i];
        for (var j = 2; j < sankeyItem.length; j++) {
            sankeyItem[j] = parseInt(sankeyItem[j]);
            sankeyValue[i] = sankeyItem;
        }
    }

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'From');
    data.addColumn('string', 'To');
    data.addColumn('number', 'Weight');

    data.addRows(sankeyValue);

    var options = {};

    var chart = new google.visualization.Sankey(document.getElementById('sankey'));
    chart.draw(data, options);
}

function drawLineChart(lineArgs) {

    var lineValue = lineArgs.argsData;

    for (var i = 0; i < lineValue.length; i++) {
        var lineItem = lineValue[i];
        for (var j = 0; j < lineItem.length; j++) {
            lineItem[j] = parseInt(lineItem[j]);
            lineValue[i] = lineItem;
        }
    }

    var data = new google.visualization.DataTable();

    data.addColumn('number', lineArgs.hAxis);


    var lineName = lineArgs.lineName;

    for (var k = 0; i < lineName.length; i++) {
        data.addColumn('number', lineName[0]);
    }

    var lineName = lineArgs.lineName;
    for (var l = 0; l < lineName.length; l++) {
        data.addColumn('number', lineName[i]);
    }

    data.addRows(lineValue);

    var options = {
        chart: {
            title: lineArgs.title,
            subtitle: lineArgs.subTitle
        }
    };

    var chart = new google.charts.Line(document.getElementById('line'));

    chart.draw(data, google.charts.Line.convertOptions(options));
}

function changeCommodityInput() {
    var option = $("#commodity").val();

    switch (option) {
        case "factory":
            hideAllCommodityInput();
            $("#factory").show();
            break;
        case "brand":
            hideAllCommodityInput();
            $("#factory").show();
            $("#brand").show();
            break;
        case "medicine":
            hideAllCommodityInput();
            $("#factory").show();
            $("#brand").show();
            $("#medicine").show();
            break;
        default:
            hideAllCommodityInput();
    }
}

function changeTimeInput() {
    var option = $("#time").val();

    switch (option) {
        case "year":
            hideAllTimeInput();
            $("#year").show();
            break;
        case "quarter":
            hideAllTimeInput();
            $("#year").show();
            $("#quarter").show();
            break;
        case "month":
            hideAllTimeInput();
            $("#year").show();
            $("#quarter").show();
            $("#month").show();
            break;
        default:
            hideAllTimeInput();
    }
}

function hideAllCommodityInput() {
    $("#factory").hide();
    $("#brand").hide();
    $("#medicine").hide();
}

function hideAllTimeInput() {
    $("#year").hide();
    $("#quarter").hide();
    $("#month").hide();
}