//app.js
var appData = {
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (!res.authSetting['scope.userInfo']) {
          // 申请授权获取用户信息
          wx.authorize({
            scope: 'scope.userInfo',
          })
        }

        if (!res.authSetting['scope.userLocation']) {
          // 申请授权获取定位
          wx.authorize({
            scope: 'scope.userLocation',
          })
        }
      }
    })  
  },

  globalData: {
    userInfo: null,
    domain: 'http://192.168.3.17:9527',
    userBasePath: '/pkast.user/pkast/user/',
    locationBasePath: '/pkast.location/pkast/location/',
    bbsBasePath: '/pkast.bbs/pkast/bbs/'
  }
}

App(appData)