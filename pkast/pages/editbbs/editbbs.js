var xiaoquId=''
var wxNo = ''
var selectType = 'TXNC'
var bbsProperties = {}

var err_reg_bbs_type_invalid = -2;
var err_reg_suc = 0;
var err_reg_car_num_invalid = 1;
var err_reg_phone_num_invalid = 2;
var err_reg_wx_num_invalid = 3;
var err_reg_xiaoqu_invalid = 4;
var err_reg_park_num_invalid = 5;
var err_reg_price_invalid = 6;
var err_reg_time_invalid = 7;
var err_reg_day_invalid = 8;
var err_reg_short_desc_invalid = 9;

var errMsgs = ['发布消息有误！','','','车牌号有误！','手机号有误！','未获取微信名！','未获取位置信息！','车位号有误！','价格填写错误！','时间填写错误！','时间填写错误！','物品填写错误'];

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
    var page = this;
    setTimeout(function(){
      // 发布信息
      editbbsData.getBbsProperties(page)
      wx.showLoading({
        title: '',
        icon: 'loading'
      })
      wx.request({
        url: getApp().globalData.domain + getApp().globalData.bbsBasePath + '0.0.1/add-bbs?xiaoquId=' + xiaoquId,
        header: {
          'content-type': 'application/json',
        },
        method: 'POST',
        data: {
          type: selectType,
          properties: bbsProperties
        },
        success: function (response) {
          if(response.data == err_reg_suc){
            wx.showToast({
              title: '发布成功',
              icon: 'success'
            })
            setTimeout(function () {
              wx.navigateBack({
                delta: 1
              })
            }, 1500);
          }
          else{
            var errmsg = errMsgs[response.data + 2];
            wx.showToast({
              title: errmsg,
              icon: 'none'
            })
          }
        },
        fail: function (response) {
          wx.showToast({
            title: '差点就成功了..',
            icon: 'none'
          })
        },
        complete:function(request){
          wx.hideLoading();
        }
      })
    }, 100)    
  }
}

Page(editbbsData)