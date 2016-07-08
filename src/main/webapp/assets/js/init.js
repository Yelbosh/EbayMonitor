require.config({
    paths: {
        jquery: './public/jquery-1.8.3.min',
        highcharts: './public/highcharts',
        form: './public/jquery.form'
    },
    shim: {
    	highcharts: {
    		exports: "Highcharts",
    		deps: ["jquery"]
    	}
    }
});
var timer;

require(['jquery', 'highcharts', 'form'], function () {
	$(function() {
	    $(document).ready(function() {
	    	//get the query id
	    	var id;
	    	var url = location.search;
	    	 if (url.indexOf("?") != -1) {
	    		 var str = url.substr(1);
	    		 strs = str.split("=");
	    		 id = strs[1];
	    		 console.log(id);
	    	}else{
	    		alert("you did not provide a server!");
	    		return;
	    	}
	    	//get the init data
	    	var init_data = [];
	    	$.ajax({
	    		url: '/monitor.action?type=1&id='+id,
	    		async:false,
                dataType: 'json',
                cache: false,
                success: function (reqdata) {
//                	console.log(reqdata.data);
                	init_data = reqdata;
                },
                error: function () {
                    console.log('error request');
                }
            });
	    	//Highcharts settings
	    	Highcharts.createElement('link', {
	    		href: 'http://fonts.googleapis.com/css?family=Unica+One',
	    		rel: 'stylesheet',
	    		type: 'text/css'
	    	}, null, document.getElementsByTagName('head')[0]);

	    	Highcharts.theme = {
	    		colors: ["#2b908f", "#90ee7e", "#f45b5b", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
	    			"#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
	    		chart: {
	    			backgroundColor: {
	    				linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
	    				stops: [
	    					[0, '#2a2a2b'],
	    					[1, '#3e3e40']
	    				]
	    			},
	    			style: {
	    				fontFamily: "'Unica One', sans-serif"
	    			},
	    			plotBorderColor: '#606063'
	    		},
	    		title: {
	    			style: {
	    				color: '#E0E0E3',
	    				textTransform: 'uppercase',
	    				fontSize: '20px'
	    			}
	    		},
	    		subtitle: {
	    			style: {
	    				color: '#E0E0E3',
	    				textTransform: 'uppercase'
	    			}
	    		},
	    		xAxis: {
	    			gridLineColor: '#707073',
	    			labels: {
	    				style: {
	    					color: '#E0E0E3'
	    				}
	    			},
	    			lineColor: '#707073',
	    			minorGridLineColor: '#505053',
	    			tickColor: '#707073',
	    			title: {
	    				style: {
	    					color: '#A0A0A3'

	    				}
	    			}
	    		},
	    		yAxis: {
	    			gridLineColor: '#707073',
	    			labels: {
	    				style: {
	    					color: '#E0E0E3'
	    				}
	    			},
	    			lineColor: '#707073',
	    			minorGridLineColor: '#505053',
	    			tickColor: '#707073',
	    			tickWidth: 1,
	    			title: {
	    				style: {
	    					color: '#A0A0A3'
	    				}
	    			}
	    		},
	    		tooltip: {
	    			backgroundColor: 'rgba(0, 0, 0, 0.85)',
	    			style: {
	    				color: '#F0F0F0'
	    			}
	    		},
	    		plotOptions: {
	    			series: {
	    				dataLabels: {
	    					color: '#B0B0B3'
	    				},
	    				marker: {
	    					lineColor: '#333'
	    				}
	    			},
	    			boxplot: {
	    				fillColor: '#505053'
	    			},
	    			candlestick: {
	    				lineColor: 'white'
	    			},
	    			errorbar: {
	    				color: 'white'
	    			}
	    		},
	    		legend: {
	    			itemStyle: {
	    				color: '#E0E0E3'
	    			},
	    			itemHoverStyle: {
	    				color: '#FFF'
	    			},
	    			itemHiddenStyle: {
	    				color: '#606063'
	    			}
	    		},
	    		credits: {
	    			style: {
	    				color: '#666'
	    			}
	    		},
	    		labels: {
	    			style: {
	    				color: '#707073'
	    			}
	    		},

	    		drilldown: {
	    			activeAxisLabelStyle: {
	    				color: '#F0F0F3'
	    			},
	    			activeDataLabelStyle: {
	    				color: '#F0F0F3'
	    			}
	    		},

	    		navigation: {
	    			buttonOptions: {
	    				symbolStroke: '#DDDDDD',
	    				theme: {
	    					fill: '#505053'
	    				}
	    			}
	    		},

	    		// scroll charts
	    		rangeSelector: {
	    			buttonTheme: {
	    				fill: '#505053',
	    				stroke: '#000000',
	    				style: {
	    					color: '#CCC'
	    				},
	    				states: {
	    					hover: {
	    						fill: '#707073',
	    						stroke: '#000000',
	    						style: {
	    							color: 'white'
	    						}
	    					},
	    					select: {
	    						fill: '#000003',
	    						stroke: '#000000',
	    						style: {
	    							color: 'white'
	    						}
	    					}
	    				}
	    			},
	    			inputBoxBorderColor: '#505053',
	    			inputStyle: {
	    				backgroundColor: '#333',
	    				color: 'silver'
	    			},
	    			labelStyle: {
	    				color: 'silver'
	    			}
	    		},

	    		navigator: {
	    			handles: {
	    				backgroundColor: '#666',
	    				borderColor: '#AAA'
	    			},
	    			outlineColor: '#CCC',
	    			maskFill: 'rgba(255,255,255,0.1)',
	    			series: {
	    				color: '#7798BF',
	    				lineColor: '#A6C7ED'
	    			},
	    			xAxis: {
	    				gridLineColor: '#505053'
	    			}
	    		},

	    		scrollbar: {
	    			barBackgroundColor: '#808083',
	    			barBorderColor: '#808083',
	    			buttonArrowColor: '#CCC',
	    			buttonBackgroundColor: '#606063',
	    			buttonBorderColor: '#606063',
	    			rifleColor: '#FFF',
	    			trackBackgroundColor: '#404043',
	    			trackBorderColor: '#404043'
	    		},

	    		// special colors for some of the
	    		legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
	    		background2: '#505053',
	    		dataLabelsColor: '#B0B0B3',
	    		textColor: '#C0C0C0',
	    		contrastTextColor: '#F0F0F3',
	    		maskColor: 'rgba(255,255,255,0.3)'
	    	};
	    	Highcharts.setOptions(Highcharts.theme);
	        Highcharts.setOptions({
	            global: {
	                useUTC: false
	            }
	        });

	        var chart;
	        $('#container').highcharts({
	            chart: {
	                type: 'spline',
	                animation: Highcharts.svg,
	                // don't animate in old IE               
	                marginRight: 10,
	                events: {
	                    load: function() {
	                    	 // set up the updating of the chart each second             
	                        var series = this.series[0];
	                        var series2 = this.series[1];
	                        timer = setInterval(function() {
	                        	$.ajax({
		            	    		url: '/monitor.action?type=0&id='+id,
		                            dataType: 'json',
		                            cache: false,
		                            success: function (data) {
		                            	var x = data.data.time,
		                            	y1 = data.data.cpu;
		                            	y2 = data.data.mem;
		                            	series.addPoint([x, y1], true, true);
			                            series2.addPoint([x, y2], true, true);
		                            },
		                            error: function () {
		                                console.log('error request');
		                            }
		                        });
	                        },30000);
	                    }
	                }
	            },
	            title: {
	                text: 'EBay CPU/Memory Usage Trend'
	            },
	            xAxis: {
	                type: 'datetime',
	                tickPixelInterval: 150
	            },
	            yAxis: [{
	                title: {
	                    text: 'CPU'
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#808080'
	                }]
	            },
	            {
	                title: {
	                    text: 'Memory'
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#626263'
	                }]
	            },
	            ],
	            tooltip: {
	                formatter: function() {
	                    return '<b>' + this.series.name + '</b><br/>' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + Highcharts.numberFormat(this.y, 2);
	                }
	            },
	            legend: {
	                enabled: false
	            },
	            exporting: {
	                enabled: false
	            },
	            series: [{
	                name: 'CPU Usage',
	                data: (function() {
	                    // generate an array of random data                             
	                    var data = [];
//	                    while(init_data.data == null) ;
	                    for(i=0;i<init_data.data.length;i++){
                        	data.push({
	                            x: init_data.data[i].time,
	                            y: init_data.data[i].cpu
	                        });
                        }
	                    return data;
	                })()
	            },
	            {
	                name: 'Memory Usage',
	                data: (function() {                          
	                    var data = [];
	                    for(i=0;i<init_data.data.length;i++){
                        	data.push({
	                            x: init_data.data[i].time,
	                            y: init_data.data[i].mem
	                        });
                        }
	                    return data;
	                })()
	            }]
	        });
	    });

	});
});