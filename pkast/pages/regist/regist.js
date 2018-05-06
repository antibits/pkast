var err_reg_user_invalid = -1;
var err_reg_suc = 0;
var err_reg_car_num_invalid = 1;
var err_reg_phone_num_invalid = 2;
var err_reg_wx_num_invalid = 3;
var err_reg_xiaoqu_invalid = 4;

var errMsgs = ['用户信息错误！','','车牌号填写错误！','手机号填写错误！','未获取微信名！','未获取位置信息！'];

var wxNo=''
var carNo=''
var phoneNum=''
var xiaoquId=''

var registData={
  data: {
    cityCode: [['鄂', '京', '津', '冀', '晋', '内蒙古', '辽', '吉', '黑', '沪', '苏', '浙', '皖', '闽', '赣', '鲁', '豫', '湘', '粤', '桂', '琼', '川', '贵', '云', '藏', '陕', '甘', '青', '宁', '新', '港', '澳', '台'], ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']],
    cityCodeIndex: [0, 0],
  },

  bindMultiPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      cityCodeIndex: e.detail.value
    })
  },

  /**
   * 编辑车牌号
   */
  bindNumEdit: function (e) {
    carNo = e.detail.value;
  },

  /**
   * 编辑电话号码
   */
  bindPhoneEdit: function(e){
    phoneNum = e.detail.value;
  },

  addUserInfo: function (wxNo, carNumber, phoneNum) {
    wx.showLoading({
      title: '加载中',
      icon: 'loading'
    })
    wx.request({
      url: getApp().globalData.domain + getApp().globalData.userBasePath + '0.0.1/add-user',
      header: {
        'content-type': 'application/json',
      },
      method:'POST',
      data:{
        wxNo:wxNo,
        xiaoqu:xiaoquId,
        phoneNum:phoneNum,
        carNo:carNumber
      },
      success:function(response){
        if(response.data == err_reg_suc){
          wx.showToast({
            title: '注册成功！',
            icon: 'success'
          })
          setTimeout(function () {
            wx.navigateBack({
              delta: 1
            })
          }, 1500);
        }
        else {
          var errmsg = errMsgs[response.data + 1];
          wx.showToast({
            title: errmsg,
            icon: 'warn'
          })
        }
      },
      fail:function(response){
        wx.showToast({
          title: '请检查网络~',
          icon: 'none'
        })
      },
      complete:function(request){
        wx.hideLoading();
      }
    })
  },

  /**
   * 注册用户
   */
  regist: function (e) {
    var page = this;
    setTimeout(function(){
      var carNumber = registData.data.cityCode[0][page.data.cityCodeIndex[0]] + registData.data.cityCode[1][page.data.cityCodeIndex[1]] + carNo;

      if (carNumber.length < 7) {
        wx.showToast({
          title: errMsgs[err_reg_car_num_invalid + 1],
          icon: 'none'
        })
        return;
      }
      if (phoneNum.length < 7){
        wx.showToast({
          title: errMsgs[err_reg_phone_num_invalid + 1],
          icon: 'none'
        })
        return;
      }

      registData.addUserInfo(wxNo, carNumber, phoneNum);
    }, 100);    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    xiaoquId = options.xiaoquId;
    wxNo = options.wxNo;
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

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

  }
}

Page(registData)