var operatInit = 0;
var operatPrevPage = 1;
var operatNextPage = 2;

var currBbsTypeIdx = 0
var currBbsPageIdx = 1

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

    bbsTypes:['全部信息','提醒挪车','车位出租','车位转让','寻物启事','失物招领'],
    bbsTypeEnums: [null, 'TXNC', 'CWCZ', 'CWZR', 'XWQS', 'SWZL'],
    bbsTypeIndex: 0,

    bbsNews: [[{
      name: 'div',
      bindtap: 'call_0',
      attrs: {
        style: 'word-break: break-all;margin: 1em 0.2em 0.4em 0.2em;color:rgb(152,152,152);font-weight:bolder;text-align:center'
      },
      children: [{
        type: 'text',
        text: '空空如也^~^',
      }]
    }]]
  },

  notifyUnregist:function(page){
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
  },

  checkUserRegist:function(page, doIfRegistFunc){
    wx.showLoading({
      title: '',
      icon:'loading'
    })
    wx.request({
      url: getApp().globalData.domain + getApp().globalData.locationBasePath + '0.0.1/add-location',
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
          wx.showLoading({
            title: '',
            icon: 'loading'
          })
          wx.request({
            url: getApp().globalData.domain + getApp().globalData.userBasePath + '0.0.1/get-user-bycar',
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
                pkastData.notifyUnregist(page);
              }
              else{
                // 注册之外的回调
                doIfRegistFunc(response);   
              }
            },
            fail: function (response) {
              pkastData.requestErrToast();
            },
            complete:function(request){
              wx.hideLoading();
            }
          })
        }
        else {
          console.log("some thing wrong write location information." + response.data);
          pkastData.requestErrToast();
        }
      },
      fail: function (response) {
        pkastData.requestErrToast();
      },
      complete:function(request){
        wx.hideLoading();
      }
    })
  },
  
  callowner: function (e) {
    var page = this;
    setTimeout(function(){
      var carNumber = pkastData.data.cityCode[0][page.data.cityCodeIndex[0]] + pkastData.data.cityCode[1][page.data.cityCodeIndex[1]] + carNo;
      console.log(carNumber);
      if (carNumber.length < 7) {
        wx.showToast({
          title: '车牌号有误！',
          icon: 'none'
        });
        return;
      }

      pkastData.checkUserRegist(page,
        function (response) {
          var phoneNum = '116114';
          if (response.data.retCode == ret_code_suc) {
             phoneNum = response.data.data[0];
          }
          wx.makePhoneCall({
            phoneNumber: phoneNum 
          })
        })
    }, 100);
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

  refreshbbs:function(e){
    var selectedBbsType = pkastData.data.bbsTypeEnums[currBbsTypeIdx];
    pkastData.getBbs(this, selectedBbsType, operatNextPage);
  },

  bindPickLocation: function (e) {
    this.setData({
      locationIndex: e.detail.value
    })
    var selectedBbsType = pkastData.data.bbsTypeEnums[currBbsTypeIdx];
    pkastData.getBbs(this, selectedBbsType, operatInit)
  },
  
  bindPickCarNoSuffix: function (e) {
    this.setData({
      cityCodeIndex: e.detail.value
    })
  },

  bindSelectBbsType:function(e){
    if(currBbsTypeIdx == e.detail.value){
      return;
    }
    currBbsTypeIdx = e.detail.value;
    var selectedBbsType = pkastData.data.bbsTypeEnums[currBbsTypeIdx];
    
    this.setData({
      bbsTypeIndex: e.detail.value
    })

    currBbsPageIdx = 1;
    pkastData.getBbs(this, selectedBbsType, operatInit);
  },

  getLocationInfo: function (page) {
    wx.showLoading({
      title: '',
      icon: 'loading'
    })
    wx.getLocation({
      success: function (res) {
        var latitude = res.latitude
        var longitude = res.longitude

        var nearByParam = 'nearby(' + latitude + ',' + longitude + ',' + scope + ')';
        var mapUrl = txmapUrl + nearByParam;
        
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
            currBbsPageIdx = 1;
            var selectedBbsType = pkastData.data.bbsTypeEnums[currBbsTypeIdx];
            pkastData.getBbs(page, selectedBbsType, operatInit);
          }
        })
      },
      fail: function (res) {
        wx.showToast({
          title: '位置获取失败',
          icon: 'none'
        })
      },
      complete:function(request){
        wx.hideLoading();
      }
    })
  },

  showLoginErr:function(){
    wx.showToast({
      title: '微信授权登录失败~',
      icon: 'none'
    });
    wxNo = null;
  },

  getWxNo:function(page){
    wx.login({
      success:function(res){
        if(res.code){
          var code = res.code;

          wx.getUserInfo({
            withCredentials: true,
            success: function (res) {

              wxNo = res.userInfo.nickName;
            }
          });
        }
        else{
          pkastData.showLoginErr();
        }    
      },
      fail:function(res){
        pkastData.showLoginErr();
      }
    })   
  },

  getBbs: function (page, selectedBbsdType, operation){
    var requestParam = {
      pageIdx: currBbsPageIdx,
      pageSize: 10,
      xiaoquId: addrList[page.data.locationIndex].id
    };
    if(selectedBbsdType != null){
      requestParam['type'] = selectedBbsdType;
    }
    wx.showLoading({
      title: '',
      icon: 'loading'
    })
    wx.request({
      url: getApp().globalData.domain + getApp().globalData.bbsBasePath +  '0.0.1/get-bbs',
      header: {
        'content-type': 'application/json',
      },
      method: 'GET',
      data: requestParam,
      success:function(response){
        if (response.data.length == 0){
          if (operation == operatInit) {
            wx.showToast({
              title: '暂无信息发布~',
              icon:'none'
            })
            page.setData({
              bbsNews: pkastData.data.bbsNews
            });
          }
          else if(operation == operatPrevPage){
            wx.showToast({
              title: '已翻到首页~',
              icon:'none'
            })
            currBbsPageIdx = 1;
          }
          else if(operation == operatNextPage){
            wx.showToast({
              title: '已翻到最后一页',
              icon:'none'
            })
            currBbsPageIdx --;
          }
        }
        else{
          page.setData({
            bbsNews: response.data
          });
          pkastData.makeContactCallback(page);
        }        
      },
      fail:function(response){
        pkastData.requestErrToast();
      },
      complete:function(request){
        wx.hideLoading();
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
  },

  nextpage:function(e){
    currBbsPageIdx ++;
    var selectedBbsType = pkastData.data.bbsTypeEnums[currBbsTypeIdx];
    pkastData.getBbs(this, selectedBbsType, operatNextPage);
  },

  prepage: function(e){
    if(currBbsPageIdx == 1){
      wx.showToast({
        title: '已翻到首页~',
        icon: 'none'
      })
      return;
    }
    currBbsPageIdx --;
    var selectedBbsType = pkastData.data.bbsTypeEnums[currBbsTypeIdx];
    pkastData.getBbs(this, selectedBbsType, operatPrevPage);
  },

  /**
   * 定义拨号绑定
   */
  makeContactCallback:function(page){    
    for (var i = 0; i < page.data.bbsNews.length; ++i) {
      (function (bindtap) {
        var createrWxNo = page.data.bbsNews[i][0].creater;
        var phoneNum = '116114';
        page[bindtap] = function (e) {
          wx.showLoading({
            title: '',
            icon: 'loading'
          })
          wx.request({
            url: getApp().globalData.domain + getApp().globalData.userBasePath + '0.0.1/get-phone-bywx',
            header:{
              'content-type': 'application/json',
            },
            method:'GET',
            data:{
              wxNo:wxNo,
              otherWxNo:createrWxNo
            },
            success:function(response){
              if (response.data.retCode == ret_code_notreg){
                pkastData.notifyUnregist(page);
                return;
              }
              else if(response.data.retCode == ret_code_fail){
                wx.showToast({
                  title: '用户还未注册^V^',
                  icon:'none'
                })
              }
              else if(response.data.retCode == ret_code_suc){
                phoneNum = response.data.data[0];
              }
              // 拨打电话
              wx.makePhoneCall({
                phoneNumber: phoneNum
              })
            },
            fail:function(response){
              pkastData.requestErrToast();
            },
            complete:function(request){
              wx.hideLoading();
            }
          })         
        }
      })(page.data.bbsNews[i][0].bindtap)
    }
  }
}

Page(pkastData)