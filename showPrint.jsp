<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib-base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>${fns:getConfig('productName')}</title>

    <jsp:include page="/WEB-INF/views/include/head-front.jsp"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/easyui/themes/black/easyui.css">
    <script src="${ctxStatic}/easyui/jquery.easyui.min.js"></script>
    <script src="${ctxStatic}/easyui/easyui-lang-zh_CN.js"></script>
    <%--<script src="${ctxStatic}/jquery-plugin/jquery.PrintArea.js"></script>--%>
    <style>
        .header-wrap {
            position: static !important;
        }

        .alertTips {
            margin-top: 50px;
        }

        body {
            background-color: #f0f0f0
        }

        .chengjiaojiachaxun input[type="checkbox"] {
            width: 13px;
            height: 13px;
            margin: 0;
        }

        .table td {
            vertical-align: middle;
        }
        .sendProdctListNews{
            margin-top: 15px;
        }
        .sendProdctListNews th{
            font-weight: bold;
        }
        .sendProdctListNews td{
            color: #666;
        }
        .sendProdctListNews .priceTag{
            border: 1px solid #E13131;
            color: #e13131;
            padding: 1px 3px;
            border-radius: 14px;
            font-size: 12px;
        }
        .sendProdctManNews label{
            font-weight: bold;
            margin-bottom: 15px;
        }
        .sendProdctManNews label span{
            font-weight: normal;
        }
        #page2 .table td{
            font-size: 12px;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/front/top.jsp"/>

<!-- 中部 -->
<div style="margin:35px 0 20px 0;background-color: white;height: 50px;line-height: 50px">
    <div class="container">
        <p>
            <span>当前位置：</span>
            <a href="${ctxFront}/">首页&gt;</a>
            <span>送货单：${delivery.deliveryBillNo}</span>

        </p>
    </div>

</div>


<div class="container">

    <div class="chengjiaojiachaxun">

        <jsp:include page="/WEB-INF/views/order/order/delivery/includePage/delivery_show_print.jsp"/>

        <div class="noPrint">
            <jsp:include page="/WEB-INF/views/order/order/delivery/includePage/receipt_show.jsp"/>
        </div>
        <form class="pirnt form-horizontal clearfix" style="display: none">
            <div id="pageFooter">
                <div style="width: 100%">
                    <div class="pull-left" style="width: 50%;">
                        <p style="font-size: 10px;font-weight: bold ">提示:①白联用于采材宝结算  ②红联供方留底  ③黄联采方留底</p>
                        <p style="font-size: 10px;padding-left: 35px;font-weight: bold">您需要妥善保管此单据，此单据为双方结算的重要依据</p>
                    </div>
                    <div class="pull-left sendProdctManNews" style="width: 50%">
                        <div style="padding-left: 110px;">
                            <p style="font-size: 10px;font-weight: bold">签收人：<span>${delivery.signer}</span></p>
                            <p style="font-size: 10px;font-weight: bold">签收日期：<span><fmt:formatDate value="${delivery.signTime}" pattern="YYYY-MM-dd HH:mm:ss"/></span></p>
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <div class="text-center" style="margin-top: 15px">
            <a class="btn btn-danger" style="width: auto" href="javascript:preview2()">打印送货单</a>
        </div>
    </div>
    <!--startprint-->
    <div id="print" style="display: none;"></div>
    <!--endprint-->

</div>


<jsp:include page="/WEB-INF/views/front/foot.jsp"/>
<script type="text/javascript">
    var imgNum=$('img').length;
    $('img').load(function(){
        if(!--imgNum){
            // 加载完成
            console.log("aaa")
        }
    });

    var canPrint = false;
   function hello(){
       canPrint = true;
   }
   //使用方法名字执行方法
    if($("#deliveryPortraitPreview li").length>0){
        var t1 = window.setTimeout(hello,2500)
    }else {
        canPrint = true;
    }


    var total=0;

    function preview2() {
        if(!canPrint){
            alertL("请等待图片加载完再执行!");
            return false;
        }
        //初始化清空打印区内容
        document.getElementById('print').innerHTML = "";
        var content = "";
        $('.noPrint').remove();
        var detailsLists = $.parseJSON('${detailsList}');
        var pageTitle = $("#pageTitle").html();
        for (var i = 0; i < detailsLists.length; i++) {
            var detailsList = detailsLists[i];
            var pageTitle = document.getElementById('pageTitle').innerHTML; //表头
            content = content + '<div style="position: relative;height: 100%">' + pageTitle;
            //Tabel 数据
            var TableHtml = '';
            TableHtml = getTabelHtml(detailsList, TableHtml);
            content = content + TableHtml;

            var pageFooter = document.getElementById('pageFooter').innerHTML; //标底
            content = content + pageFooter;
            content = content + '<div ><div style="text-align: center;opacity: 0">第\'+(i+1)+\'页，共\'+detailsLists.length+\'页第\'+(i+1)+\'页，共\'+detailsLists.length+\'页第\'+(i+1)+\'页，共\'+detailsLists.length+\'页第\'+(i+1)+\'页，共\'+detailsLists.length+\'页</div>';
            content = content + '<div style="page-break-after:always;text-align: center;">第'+(i+1)+'页，共'+detailsLists.length+'页</div></div></div>';

        }

        $("#print").append(content);
        total=0;
        bdhtml = window.document.body.innerHTML;//获取当前页的html代码

        window.document.body.innerHTML = $("#print").html();
        window.print();
        window.document.body.innerHTML = bdhtml;
    }

    function getTabelHtml(detailsList, html) {
        html = '<div style="min-height: 650px;">';
        html += '<table class="table table-bordered" >';
        html += '<thead>';
        html += '<tr>';
        html += '<th style="width: 30px;font-size: 10px;padding: 2px ">序号</th>';
        html += '<th style="width: 360px;font-size: 10px;padding: 2px ">商品名称</th>';
        html += '<th style="width: 120px;font-size: 10px;padding: 2px ">规格</th>';
        html += '<th style="width: 65px;font-size: 10px;padding: 2px ">品牌</th>';
        html += '<th style="width: 40px;font-size: 10px;padding: 2px ">单位</th>';
        html += '<th style="width: 65px;font-size: 10px;padding: 2px ">实发数量</th>';
        html += '<th style="width: 65px;font-size: 10px;padding: 2px ">实收数量</th>';
        html += '</tr>';
        html += '</thead>';
        html += '<tbody>';
        var totalQty = 0;
        var totalAmount = 0;
        console.log(detailsList,'666');
        for (var i = 0; i < detailsList.length; i++) {
            var details = detailsList[i];
            html += '<tr class="detailsRow">';
            total = total+1;
            html += '<td style="font-size: 8px;padding: 2px;text-align: center; ">' + total + '</td>';
            html += '<td style="font-size: 8px;padding: 2px;max-width:370px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap; ">' + details.productName + '</td>';
            html += '<td style="font-size: 8px;padding: 2px ">' + details.standard + '</td>';
            html += '<td style="font-size: 8px;padding: 2px ">' + details.brandName + '</td>';
            html += '<td style="font-size: 8px;padding: 2px ">' + details.unitName + '</td>';
            html += '<td style="font-size: 8px;padding: 2px ">' + details.deliveryQty + '</td>';
            if (details.deliveryBillStatus == '01'
                || details.deliveryBillStatus == 'undefined'
                || details.deliveryBillStatus == undefined){
                html += '<td style="font-size: 8px;padding: 2px ">&nbsp;</td>';
//                html += '<td>' + (parseFloat(details.deliveryQty) * parseFloat(details.unitPrice)).toFixed(2) + '</td>';
            }else {
                html += '<td style="font-size: 8px;padding: 2px ">'+details.actualQty+'</td>';
//                html += '<td>' + (parseFloat(details.actualQty) * parseFloat(details.unitPrice)).toFixed(2) + '</td>';
            }

            html += '</tr>';
            totalQty = parseFloat(details.deliveryQty) + parseFloat(totalQty);
            if (details.deliveryBillStatus == '01'
                || details.deliveryBillStatus == 'undefined'
                || details.deliveryBillStatus == undefined){
                totalAmount = (parseFloat(details.deliveryQty) * parseFloat(details.unitPrice) + parseFloat(totalAmount)).toFixed(2);
            }else {
                totalAmount = (parseFloat(details.actualQty) * parseFloat(details.unitPrice) + parseFloat(totalAmount)).toFixed(2);
            }
        }
        if(detailsList.length<20){
            var newLength=20-detailsList.length;
            for(var j=0;j<newLength;j++){
                total=total+1;
               var trHtml= '<tr>'+
                               '<td style="font-size: 8px;padding: 2px;text-align: center; ">'+total+'</td>'+
                               '<td style="font-size: 8px;padding: 2px; "></td>'+
                               '<td style="font-size: 8px;padding: 2px; "></td>'+
                               '<td style="font-size: 8px;padding: 2px; "></td>'+
                               '<td style="font-size: 8px;padding: 2px; "></td>'+
                               '<td style="font-size: 8px;padding: 2px; "></td>'+
                               '<td style="font-size: 8px;padding: 2px; "></td>'+
                            '</tr>';
               html+=trHtml;
            }
        }
        html += '</tbody>';
        html += '</table>';
        html +='</div>';

        return html;
    }

    $(function () {
        alertL("模板为标准的A4纸尺寸，建议使用三联打印纸进行打印");
    })
</script>

</body>
</html>