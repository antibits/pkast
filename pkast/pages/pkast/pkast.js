var domain = 'http://eprte5.natappfree.cc'
var userBasePath = '/pkast.user/pkast/user/'
var locationBasePath = '/pkast.location/pkast/location/'
var bbsBasePath = '/pkast.bbs/pkast/bbs/'

var txmapUrl = 'http://apis.map.qq.com/ws/place/v1/search?key=d84d6d83e0e51e481e50454ccbe8986b&keyword=小区&boundary='
// 800米范围
var scope = 800;

var ret_code_suc = 0
var ret_code_fail = 1
var ret_code_notreg = 2

var wxNo=''
var carNo = ''
var addrList = [];
var addressNames = [];


var pkastData = {

  /**
   * 页面的初始数据
   */
  data: {
    locations: [],
    locationIndex: 0,
    cityCode: [['鄂', '京', '津', '冀', '晋', '内蒙古', '辽', '吉', '黑', '沪', '苏', '浙', '皖', '闽', '赣', '鲁', '豫', '湘', '粤', '桂', '琼', '川', '贵', '云', '藏', '陕', '甘', '青', '宁', '新', '港', '澳', '台'], ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']],
    cityCodeIndex: [0, 0],

    bbsNews: [[{
      name: 'div',
      bindtap: 'call_13682458563',
      attrs: {
        class: 'ul_style',
        style: 'color:red'
      },
      children: [{
        type: 'text',
        text: 'call her!',
      }, {
        name: "span",
        attrs: {
          style: "color:blue",
          class: "span_class"
        },
        children: [{
          type: "text",
          text: '我是span标签,哈哈哈哈'
        }]
      }]
    }],
    [{
      name: 'div',
      bindtap: 'call_18986287022',
      attrs: {
        class: 'ul_style',
      },
      children: [{
        type: 'text',
        text: 'call me!'
      }]
    }]
    ]
  },
  
  callowner: function (e) {
    var carNumber = pkastData.data.cityCode[0][this.data.cityCodeIndex[0]] + pkastData.data.cityCode[1][this.data.cityCodeIndex[1]] + carNo;
    console.log(carNumber);
    if(carNumber.length < 7){
      wx.showToast({
        title: '车牌号有误！',
        icon: 'none'
      });
      return;
    }

    wx.request({
      url: domain + locationBasePath + '0.0.1/add-location',
      header:{
        'content-type': 'application/json',
      },
      method:'POST',
      data: {
        id: addrList[pkastData.data.locationIndex].id,
        xiaoquName: addrList[pkastData.data.locationIndex].address,
        locat_x_min: addrList[pkastData.data.locationIndex].location_x,
        locat_x_max: addrList[pkastData.data.locationIndex].location_x,
        locat_y_min: addrList[pkastData.data.locationIndex].location_y,
        locat_y_max: addrList[pkastData.data.locationIndex].location_y
      }, 
      success:function(response){
        if(response.data == true){
          wx.request({
            url: domain + userBasePath + '0.0.1/get-user-bycar',
            header: {
              'content-type': 'application/json',
            },
            data: {
              wxNo: wxNo,
              carNo: carNumber
            },
            success: function (response) {
              if (response.data.retCode == ret_code_suc) {
                // 成功，呼叫
                wx.makePhoneCall({
                  phoneNumber: response.data.data[0],
                })
              }
              else if (response.data.retCode == ret_code_notreg) {
                // 未注册
                wx.showModal({
                  title: '您还未注册~',
                  content: '您需先注册车主信息，才能发起呼叫哦~',
                  success: function (res) {
                    if (res.confirm) {

                      wx.navigateTo({
                        url: '/pages/regist/regist?xiaoquId=' + addrList[pkastData.data.locationIndex].id + '&wxNo=' + wxNo,
                      })
                    }
                  }
                })
              }
            },
            fail: function (response) {
              pkastData.requestErrToast();
            },
          })
        }
        else{
          console.log("some thing wrong write location information." + response.data);
          pkastData.requestErrToast();
        }
      },
      fail: function (response){
        pkastData.requestErrToast();
      }
    })   
  },

  requestErrToast:function(){
    wx.showToast({
      title: '请检查网络~',
      icon: 'none'
    })
  },

  addbbs: function(e){
    console.log('add bbs.');
    wx.navigateTo({
      url: '/pages/editbbs/editbbs',
    })
  },

  bindPickerChange: function (e) {
    this.setData({
      locationIndex: e.detail.value
    })
  },
  
  bindMultiPickerChange: function (e) {
    this.setData({
      cityCodeIndex: e.detail.value
    })
  },

  getLocationInfo: function (page) {
    wx.getLocation({
      success: function (res) {
        var latitude = res.latitude
        var longitude = res.longitude

        var nearByParam = 'nearby(' + latitude + ',' + longitude + ',' + scope + ')';
        var mapUrl = txmapUrl + nearByParam;

        console.log(nearByParam);
        wx.request({
          url: mapUrl,
          success: function (res) {
            if (res.data.status != 0 || res.data.data.length == 0) {
              console.log('error location.');
              return;
            }
            // 先清空addrList
            addrList = [];
            addressNames = [];
            for (var i = 0; i < res.data.data.length; i++) {
              addrList[i] = {
                id: res.data.data[i].id,
                address: res.data.data[i].title,
                location_x: res.data.data[i].location.lat,
                location_y: res.data.data[i].location.lng
              };
              addressNames[i] = res.data.data[i].title;
            }
            page.setData({
              locations:addressNames,
              locationIndex:0
            });
            //console.log(addrList);
          }
        })
      },
      fail: function (res) {
        wx.showToast({
          title: '位置获取失败',
          icon: 'none'
        })
      }
    })
  },

  getWxNo:function(page){
    wx.getUserInfo({
      success:function(res){
        wxNo = res.userInfo.nickName;
      }
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    pkastData.getLocationInfo(this);
    pkastData.getWxNo(this);
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  /**
   * 编辑车牌号
   */
  bindNumEdit: function (e) {
    carNo = e.detail.value;
  }
  
}

/**
 * 定义拨号绑定
 */
for (var i = 0; i < pkastData.data.bbsNews.length; ++i) {
  (function (bindtap) {
    pkastData[bindtap] = function(e){
      wx.makePhoneCall({
        phoneNumber: bindtap.substring(5),
      })
    }    
  })(pkastData.data.bbsNews[i][0].bindtap)
}

Page(pkastData)