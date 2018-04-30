var domain = 'http://6nq5bj.natappfree.cc'
var userBasePath = '/pkast.user/pkast/user/'
var locationBasePath = '/pkast.location/pkast/location/'
var bbsBasePath = '/pkast.bbs/pkast/bbs/'
var xiaoquId=''
var wxNo = ''
var selectType = 'TXNC'
var bbsProperties = {}

var TXNC= {
  parkNo: '',
  carNo:''
}

var CWCZ= {
  parkNo: '',
  period:0,
  price:0
}

var CWZR= {
  parkNo: '',
  price:0
}

var SWZL= {
  shortDesc: '',
  dayAgo:0
}

var XWQS= {
  shortDesc: '',
  dayAgo: 0
}

var editbbsData = {
  data: {
    items: [
      {
        name: 'TXNC', value: '提醒挪车', checked: 'true'
      },
      {
        name: 'CWCZ', value: '车位出租'
      },
      {
        name: 'CWZR', value: '车位转让'
      },
      {
        name: 'SWZL', value: '失物招领'
      },
      {
        name: 'XWQS', value: '寻物启事'
      },
    ],

    HIDDEN_TXNC: false,
    HIDDEN_CWCZ: true,
    HIDDEN_CWZR: true,
    HIDDEN_SWZL: true,
    HIDDEN_XWQS: true
  },

  edit_txnc_parkNo:function(e){
    TXNC.parkNo = e.detail.value;
  },
  edit_txnc_carNo: function (e) {
    TXNC.carNo = e.detail.value;
  },

  edit_cwcz_parkNo: function (e) {
    CWCZ.parkNo = e.detail.value;
  },
  edit_cwcz_period: function (e) {
    CWCZ.period = e.detail.value;
  },
  edit_cwcz_price: function (e) {
    CWCZ.price = e.detail.value;
  },

  edit_cwzr_parkNo: function (e) {
    CWZR.parkNo = e.detail.value;
  },
  edit_cwzr_price: function (e) {
    CWZR.price = e.detail.value;
  },

  edit_swzl_shortDesc: function (e) {
    SWZL.shortDesc = e.detail.value;
  },
  edit_swzl_dayAgo: function (e) {
    SWZL.dayAgo = e.detail.value;
  },

  edit_xwqs_shortDesc: function (e) {
    XWQS.shortDesc = e.detail.value;
  },
  edit_xwqs_dayAgo: function (e) {
    XWQS.dayAgo = e.detail.value;
  },

  onLoad: function (options) {
    xiaoquId = options.xiaoquId;
    wxNo = options.wxNo;

    editbbsData.initBbsProperties();
  },

  initBbsProperties:function(){
    var properties = {
      creater:wxNo,
      xiaoquId:xiaoquId
    };
    bbsProperties = properties;
  },

  getBbsProperties:function(page){
    editbbsData.initBbsProperties();

    switch(selectType){
      case 'TXNC':
        bbsProperties['parkNo'] = TXNC.parkNo;
        bbsProperties['carNo'] = TXNC.carNo;
        break;
      case 'CWCZ':
        bbsProperties['parkNo'] = CWCZ.parkNo;
        bbsProperties['period'] = CWCZ.period;
        bbsProperties['price'] = CWCZ.price;
        break;
      case 'CWZR':
        bbsProperties['parkNo'] = CWZR.parkNo;
        bbsProperties['price'] = CWZR.price;
        break;
      case 'SWZL':
        bbsProperties['shortDesc'] = SWZL.shortDesc;
        bbsProperties['dayAgo'] = SWZL.dayAgo;
        break;
      case 'XUQS':
        bbsProperties['shortDesc'] = SWZL.shortDesc;
        bbsProperties['dayAgo'] = SWZL.dayAgo;
        break;
    }
  },
  
  radioChange: function (e) {
    selectType = e.detail.value;

    for(var i = 0; i < editbbsData.data.items.length; i++){
      var hiddenFlag = 'HIDDEN_' + editbbsData.data.items[i].name;
      var changeData = {};
      if (selectType == editbbsData.data.items[i].name){ 
        changeData[hiddenFlag]=false;
        this.setData(changeData);
      }
      else{
        changeData[hiddenFlag] = true;
        this.setData(changeData);
      }
    }
  },

  pulish:function(e){
    // 发布信息
    editbbsData.getBbsProperties(this)
    wx.request({
      url: domain + bbsBasePath + '0.0.1/add-bbs?xiaoquId='+xiaoquId,
      header: {
        'content-type': 'application/json',
      },
      method: 'POST',
      data: {
        type: selectType,
        properties: bbsProperties
      },
      success: function (response) {
        wx.showToast({
          title: '发布成功',
          icon: 'success'
        })
        setTimeout(function () {
          wx.navigateBack({
            delta: 1
          })
        }, 1500);
      },
      fail: function(response){
        wx.showToast({
          title: '差点就成功了..',
          icon:'none'
        })
      }
    })
  }
}

Page(editbbsData)