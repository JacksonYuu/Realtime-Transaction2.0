<%--
  Created by IntelliJ IDEA.
  User: 拼命三石
  Date: 2019/11/9
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>

    <title>天猫双十一实时交易可视化系统 - JacksonYuu</title>

    <meta name="Keywords" content="天,猫,天猫,双十一">

    <meta name="description" content="天猫双十一实时交易可视化系统">

    <style type="text/css">

        * {
            margin: 0;
            padding: 0;
        }
        body {
            font-size: 12px;
            font-family: "微软雅黑";
            color: black;
        }
        /*
        S top
         */
        .top {
            width: 100%;
            height: 60px;
            background-color: rgb(229,229,229);
            position: absolute;
            z-index: 1;
        }
        .top .t-header {
            width: 80%;
            height: 60px;
            margin: 0 auto;
        }
        .top .t-header .t-logo {
            float: left;
        }
        .top .t-header .t-select {
            background-color: rgb(229,229,229);
            width: 20%;
            height: 30px;
            display: block;
            margin: 15px 0 15px 25%;
            outline: medium;
            font-size: 14px;
            color: salmon;
            float: left;
        }
        .top .t-header .t-text span {
            font-size: 20px;
            color: black;
            float: right;
            line-height: 60px;
        }
        /*
        E top
         */
        /*
        S content
         */
        .content {
            width: 100%;
            height: 100%;
        }
        /*
        E content
         */
    </style>

    <script src="/js/echarts.min.js"></script>

    <script src="/js/china.js"></script>

    <script src="/js/jquery.min.js"></script>

    <script>

        $(document).ready(function() {

            $('.t-select').change(function () {

                if (window.getTiming != null) {

                    window.clearInterval(getTiming);

                    window.getTiming = null;
                }

                var select = $('.t-select').find("option:selected");

                var text = select.text();

                var value = select.val();

                BJData.length = 0;

                series.length = 0;

                myChart.clear();

                distance = text;

                position = value;

                setBJData(text, value);

                changePosition(text);
            })
        })
    </script>
  </head>
  <body>

    <!-- S top -->
    <div class="top">

        <div class="t-header">

            <div class="t-logo">

                <img src="/images/logo.jpg" alt="天猫" width="160" height="60" />
            </div>

            <select class="t-select">
                <option value="1">江西省</option>
                <option value="2">北京市</option>
                <option value="3">天津市</option>
                <option value="4">上海市</option>
                <option value="5">重庆市</option>
                <option value="6">河北省</option>
                <option value="7">河南省</option>
                <option value="8">湖北省</option>
                <option value="9">湖南省</option>
                <option value="10">江苏省</option>
                <option value="11">辽宁省</option>
                <option value="12">吉林省</option>
                <option value="13">黑龙江省</option>
                <option value="14">陕西省</option>
                <option value="15">山西省</option>
                <option value="16">山东省</option>
                <option value="17">四川省</option>
                <option value="18">青海省</option>
                <option value="19">安徽省</option>
                <option value="20">海南省</option>
                <option value="21">广东省</option>
                <option value="22">贵州省</option>
                <option value="23">浙江省</option>
                <option value="24">福建省</option>
                <option value="25">台湾省</option>
                <option value="26">甘肃省</option>
                <option value="27">云南省</option>
                <option value="28">西藏自治区</option>
                <option value="29">宁夏回族自治区</option>
                <option value="30">广西壮族自治区</option>
                <option value="31">新疆维吾尔自治区</option>
                <option value="32">内蒙古自治区</option>
            </select>

            <div class="t-text">

                <span>天猫双十一实时交易可视化系统</span>
            </div>
        </div>
    </div>
    <!-- E top -->

    <!-- S content -->
    <div class="content" id="content"></div>
    <!-- E content -->

    <script type="text/javascript">

        var myChart = echarts.init(document.getElementById('content'));

        var distance = '江西省';

        var position = 1;

        var time = 5000;

        var geoCoordMap = {
            '上海': [121.4648, 31.2891],
            '东莞': [113.8953, 22.901],
            '东营': [118.7073, 37.5513],
            '中山': [113.4229, 22.478],
            '临汾': [111.4783, 36.1615],
            '临沂': [118.3118, 35.2936],
            '丹东': [124.541, 40.4242],
            '丽水': [119.5642, 28.1854],
            '乌鲁木齐': [87.9236, 43.5883],
            '佛山': [112.8955, 23.1097],
            '保定': [115.0488, 39.0948],
            '兰州': [103.5901, 36.3043],
            '包头': [110.3467, 41.4899],
            '北京': [116.4551, 40.2539],
            '北海': [109.314, 21.6211],
            '南京': [118.8062, 31.9208],
            '南宁': [108.479, 23.1152],
            '南昌': [116.0046, 28.6633],
            '南通': [121.1023, 32.1625],
            '厦门': [118.1689, 24.6478],
            '台州': [121.1353, 28.6688],
            '合肥': [117.29, 32.0581],
            '呼和浩特': [111.4124, 40.4901],
            '咸阳': [108.4131, 34.8706],
            '哈尔滨': [127.9688, 45.368],
            '唐山': [118.4766, 39.6826],
            '嘉兴': [120.9155, 30.6354],
            '大同': [113.7854, 39.8035],
            '大连': [122.2229, 39.4409],
            '天津': [117.4219, 39.4189],
            '太原': [112.3352, 37.9413],
            '威海': [121.9482, 37.1393],
            '宁波': [121.5967, 29.6466],
            '宝鸡': [107.1826, 34.3433],
            '宿迁': [118.5535, 33.7775],
            '常州': [119.4543, 31.5582],
            '广州': [113.5107, 23.2196],
            '廊坊': [116.521, 39.0509],
            '延安': [109.1052, 36.4252],
            '张家口': [115.1477, 40.8527],
            '徐州': [117.5208, 34.3268],
            '德州': [116.6858, 37.2107],
            '惠州': [114.6204, 23.1647],
            '成都': [103.9526, 30.7617],
            '扬州': [119.4653, 32.8162],
            '承德': [117.5757, 41.4075],
            '拉萨': [91.1865, 30.1465],
            '无锡': [120.3442, 31.5527],
            '日照': [119.2786, 35.5023],
            '昆明': [102.9199, 25.4663],
            '杭州': [119.5313, 29.8773],
            '枣庄': [117.323, 34.8926],
            '柳州': [109.3799, 24.9774],
            '株洲': [113.5327, 27.0319],
            '武汉': [114.3896, 30.6628],
            '汕头': [117.1692, 23.3405],
            '江门': [112.6318, 22.1484],
            '沈阳': [123.1238, 42.1216],
            '沧州': [116.8286, 38.2104],
            '河源': [114.917, 23.9722],
            '泉州': [118.3228, 25.1147],
            '泰安': [117.0264, 36.0516],
            '泰州': [120.0586, 32.5525],
            '济南': [117.1582, 36.8701],
            '济宁': [116.8286, 35.3375],
            '海口': [110.3893, 19.8516],
            '淄博': [118.0371, 36.6064],
            '淮安': [118.927, 33.4039],
            '深圳': [114.5435, 22.5439],
            '清远': [112.9175, 24.3292],
            '温州': [120.498, 27.8119],
            '渭南': [109.7864, 35.0299],
            '湖州': [119.8608, 30.7782],
            '湘潭': [112.5439, 27.7075],
            '滨州': [117.8174, 37.4963],
            '潍坊': [119.0918, 36.524],
            '烟台': [120.7397, 37.5128],
            '玉溪': [101.9312, 23.8898],
            '珠海': [113.7305, 22.1155],
            '盐城': [120.2234, 33.5577],
            '盘锦': [121.9482, 41.0449],
            '石家庄': [114.4995, 38.1006],
            '福州': [119.4543, 25.9222],
            '秦皇岛': [119.2126, 40.0232],
            '绍兴': [120.564, 29.7565],
            '聊城': [115.9167, 36.4032],
            '肇庆': [112.1265, 23.5822],
            '舟山': [122.2559, 30.2234],
            '苏州': [120.6519, 31.3989],
            '莱芜': [117.6526, 36.2714],
            '菏泽': [115.6201, 35.2057],
            '营口': [122.4316, 40.4297],
            '葫芦岛': [120.1575, 40.578],
            '衡水': [115.8838, 37.7161],
            '衢州': [118.6853, 28.8666],
            '西宁': [101.4038, 36.8207],
            '西安': [109.1162, 34.2004],
            '贵阳': [106.6992, 26.7682],
            '连云港': [119.1248, 34.552],
            '邢台': [114.8071, 37.2821],
            '邯郸': [114.4775, 36.535],
            '郑州': [113.4668, 34.6234],
            '鄂尔多斯': [108.9734, 39.2487],
            '重庆': [107.7539, 30.1904],
            '金华': [120.0037, 29.1028],
            '铜川': [109.0393, 35.1947],
            '银川': [106.3586, 38.1775],
            '镇江': [119.4763, 31.9702],
            '长春': [125.8154, 44.2584],
            '长沙': [113.0823, 28.2568],
            '长治': [112.8625, 36.4746],
            '阳泉': [113.4778, 38.0951],
            '青岛': [120.4651, 36.3373],
            '韶关': [113.7964, 24.7028],
            '河北省': [114.4995, 38.1006],
            '辽宁省': [123.1238, 42.1216],
            '黑龙江省': [127.9688, 45.368],
            '浙江省': [119.5313, 29.8773],
            '福建省': [119.4543, 25.9222],
            '山东省': [117.1582, 36.8701],
            '广东省': [113.5107, 23.2196],
            '湖北省': [114.3896, 30.6628],
            '四川省': [103.9526, 30.7617],
            '云南省': [102.9199, 25.4663],
            '甘肃省': [103.5901, 36.3043],
            '广西壮族自治区': [108.479, 23.1152],
            '宁夏回族自治区': [106.3586, 38.1775],
            '山西省': [112.3352, 37.9413],
            '吉林省': [125.8154, 44.2584],
            '江苏省': [118.8062, 31.9208],
            '安徽省': [117.29, 32.0581],
            '江西省': [116.0046, 28.6633],
            '河南省': [113.4668, 34.6234],
            '湖南省': [113.0823, 28.2568],
            '海南省': [110.3893, 19.8516],
            '贵州省': [106.6992, 26.7682],
            '陕西省': [109.1162, 34.2004],
            '青海省': [101.4038, 36.8207],
            '内蒙古自治区': [111.4124, 40.4901],
            '西藏自治区': [91.1865, 30.1465],
            '新疆维吾尔自治区': [87.9236, 43.5883],
            '上海市': [121.4648, 31.2891],
            '天津市': [117.4219, 39.4189],
            '重庆市': [107.7539, 30.1904],
            '北京市': [116.4551, 40.2539],
            '台湾省': [120.7803, 23.6525]
        };

        var convertData = function(data) {
            var res = [];
            for (var i = 0; i < data.length; i++) {
                var dataItem = data[i];
                var fromCoord = geoCoordMap[dataItem[0].name];
                var toCoord = geoCoordMap[dataItem[1].name];
                if (fromCoord && toCoord) {
                    res.push([{
                        coord: fromCoord,
                        value: dataItem[0].value
                    }, {
                        coord: toCoord,
                    }]);
                }
            }
            return res;
        };

        var color = ['#a6c84c', '#ffa022', '#46bee9'];

        var series = [];

        var BJData = [];

        function setBJData(text,ind) {

            var date = getNowFormatDate();

            if (window.getTiming != null) {

                window.clearInterval(getTiming);

                window.getTiming = null;
            }

            $.ajax({

                type: "post",
                url: "http://localhost:8080/kafka/getMessage",
                data: {
                    key : ind,
                    date : date
                },
                success: function (data) {

                    if (data.state == true) {

                        if (data.message.length > 10) {

                            BJData = eval("(" + data.message + ")");

                            changePosition(text);

                            if (window.getTiming == null) {

                                window.getTiming = setInterval("timing()", time);
                            }
                        } else {

                            alert("还没有交易数据！！！");

                            changePosition(text);

                            if (window.getTiming == null) {

                                window.getTiming = setInterval("timing()", time);
                            }
                        }
                    } else {

                        alert("连接系统失败，请稍后再试！！！");
                    }
                }
            })
        }

        function changePosition(text) {

            [
                [text, BJData]
            ].forEach(message);

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }

        function message(item, i) {

            series.push(

                {
                    type: 'lines',
                    zlevel: 2,
                    effect: {
                        show: true,
                        period: 4,
                        trailLength: 0.02,
                        symbol: 'arrow',
                        symbolSize: 5,
                    },
                    lineStyle: {
                        normal: {
                            width: 1,
                            opacity: 0.6,
                            curveness: 0.2
                        }
                    },

                    data: convertData(item[1])
                }, {
                    type: 'effectScatter',
                    coordinateSystem: 'geo',
                    zlevel: 2,
                    rippleEffect: {
                        period: 4,
                        brushType: 'stroke',
                        scale: 4
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'right',
                            offset: [5, 0],
                            formatter: '{b}'
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    symbol: 'circle',
                    symbolSize: function(val) {
                        return 4 + val[2] / 10;
                    },
                    itemStyle: {
                        normal: {
                            show: false,
                            color: '#f00'
                        }
                    },
                    data: item[1].map(function(dataItem) {
                        return {
                            name: dataItem[0].name,
                            value: geoCoordMap[dataItem[0].name].concat([dataItem[0].value])
                        };
                    }),
                },
                //被攻击点
                {
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    zlevel: 2,
                    rippleEffect: {
                        period: 4,
                        brushType: 'stroke',
                        scale: 4
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'right',
                            //			                offset:[5, 0],

                            color: '#00ffff',
                            formatter: '{b}',
                            textStyle: {
                                color: "#fff"
                            },
                            fontSize: '14'
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    symbol: 'pin',
                    symbolSize: 30,
                    itemStyle: {
                        normal: {
                            show: true,
                            color: '#f5f5f5'
                        }
                    },
                    data: [{
                        name: item[0],
                        value: geoCoordMap[item[0]].concat([100]),
                    }],
                }
            );
        }

        option = {
            backgroundColor: 'rgb(66,76,90)',

            visualMap: {
                min: 0,
                max: 100,
                calculable: true,
                color: ['#f33', 'orange', 'yellow', 'lime', 'aqua'],
                textStyle: {
                    color: '#000'
                }
            },
            geo: {
                map: 'china',
                label: {
                    emphasis: {
                        show: false
                    }
                },
                roam: true,
                layoutCenter: ['50%', '53%'],
                layoutSize: "110%",
                itemStyle: {
                    normal: {
                        color: 'rgb(61,73,91)',
                        borderColor: 'rgb(111,126,159)'
                    },
                    emphasis: {
                        color: 'rgb(52,62,78)'
                    }
                }
            },

            series: series
        };

        function getNowFormatDate() {//获取当前时间
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1<10? "0"+(date.getMonth() + 1):date.getMonth() + 1;
            var strDate = date.getDate()<10? "0" + date.getDate():date.getDate();
            var currentdate = date.getFullYear() + seperator1  + month  + seperator1  + strDate
                + " "  + date.getHours()  + seperator2  + date.getMinutes()
                + seperator2 + date.getSeconds();
            return currentdate;
        }

        setBJData(distance, position);

        function timing() {

            BJData.length = 0;

            series.length = 0;

            setBJData(distance, position);
        }

        window.getTiming;
    </script>
  </body>
</html>
