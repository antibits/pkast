var domain = 'http://u9tufv.natappfree.cc'
var userBasePath = '/pkast.user/pkast/user/'
var locationBasePath = '/pkast.location/pkast/location/'
var bbsBasePath = '/pkast.bbs/pkast/bbs/'

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
    console.log(wxNo, carNumber, phoneNum);
    
    wx.request({
      url: domain + userBasePath + '0.0.1/add-user',
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
      success:function(data){
        console.log(data);
      }
    })
  },

  /**
   * 注册用户
   */
  regist: function (e) {
    var carNumber = registData.data.cityCode[0][this.data.cityCodeIndex[0]] + registData.data.cityCode[1][this.data.cityCodeIndex[1]] + carNo;
    
    if(carNumber.length < 7 || phoneNum.length < 10){
      wx.showToast({
        title: '您的信息有误',
        icon: 'none'
      })
      return;
    }

    registData.addUserInfo(wxNo, carNumber, phoneNum);
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