var domain = 'http://6nq5bj.natappfree.cc'
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
      bindtap: 'call_0',
      attrs: {
        style: 'word-break: break-all;margin: 1em 0.2em 0.4em 0.2em;color:rgb(152,152,152);font-weight:bolder;text-align:center'
      },
      children: [{
        type: 'text',
        text: '数据加载中...',
      }]
    }]]
  },

  checkUserRegist:function(page, doIfRegistFunc){
    wx.request({
      url: domain + locationBasePath + '0.0.1/add-location',
      header: {
        'content-type': 'application/json',
      },
      method: 'POST',
      data: {
        id: addrList[page.data.locationIndex].id,
        xiaoquName: addrList[page.data.locationIndex].address,
        locat_x_min: addrList[page.data.locationIndex].location_x,
        locat_x_max: addrList[page.data.locationIndex].location_x,
        locat_y_min: addrList[page.data.locationIndex].location_y,
        locat_y_max: addrList[page.data.locationIndex].location_y
      },
      success: function (response) {
        if (response.data == true) {
          var carNumber = page.data.cityCode[0][page.data.cityCodeIndex[0]] + page.data.cityCode[1][page.data.cityCodeIndex[1]] + carNo;
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
              if (response.data.retCode == ret_code_notreg) {
                // 未注册
                wx.showModal({
                  title: '您还未注册~',
                  content: '您需先注册车主信息，才能发起呼叫哦~',
                  success: function (res) {
                    if (res.confirm) {
                      wx.navigateTo({
                        url: '/pages/regist/regist?xiaoquId=' + addrList[page.data.locationIndex].id + '&wxNo=' + wxNo,
                      })
                    }
                  }
                })
              }
              else{
                // 注册之外的回调
                doIfRegistFunc(response);   
              }
            },
            fail: function (response) {
              pkastData.requestErrToast();
            },
          })
        }
        else {
          console.log("some thing wrong write location information." + response.data);
          pkastData.requestErrToast();
        }
      },
      fail: function (response) {
        pkastData.requestErrToast();
      }
    })
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

    pkastData.checkUserRegist(this,
      function(response){
        if (response.data.retCode == ret_code_suc){
          wx.makePhoneCall({
            phoneNumber: response.data.data[0]
          })
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
    var page = this;
    console.log('add bbs.');
    pkastData.checkUserRegist(this,
      function(response){
        //只要注册成功允许跳转发布bbs
        wx.navigateTo({
          url: '/pages/editbbs/editbbs?xiaoquId=' + addrList[page.data.locationIndex].id + '&wxNo=' + wxNo,
        })
      }
    )    
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
            pkastData.getBbs(page);
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

  getBbs:function(page){
    wx.request({
      url: domain + bbsBasePath +  '0.0.1/get-bbs',
      header: {
        'content-type': 'application/json',
      },
      method: 'GET',
      data: {
        pageIdx:1,
        pageSize:10,
        xiaoquId: addrList[pkastData.data.locationIndex].id
      },
      success:function(response){
        console.log("bbs news:"  + response.data)
        page.setData({
          bbsNews:response.data
        });
      },
      fail:function(response){
        pkastData.requestErrToast();
      }
    })
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

  onPullDownRefresh: function () {
    console.log('refresh')
    pkastData.getLocationInfo(this);
    pkastData.getWxNo(this);    
    wx.stopPullDownRefresh();
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
  (function (phoneNum) {
    pkastData[phoneNum] = function(e){
      wx.makePhoneCall({
        phoneNumber: phoneNum,
      })
    }    
  })(pkastData.data.bbsNews[i][0].phoneNum)
}

Page(pkastData)