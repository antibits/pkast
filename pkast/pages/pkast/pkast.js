var carNo = {}
var pkastData = {

  /**
   * 页面的初始数据
   */
  data: {
    locations: ['清江锦城一期', '清江锦城二期', '佳兆业一期'],
    locationIndex: 0,
    provinceCode: ['鄂'],
    cityCode: [['鄂', '京', '津', '冀', '晋', '内蒙古', '辽', '吉', '黑', '沪', '苏', '浙', '皖', '闽', '赣', '鲁', '豫', '湘', '粤', '桂', '琼', '川', '贵', '云', '藏', '陕', '甘', '青', '宁', '新', '港', '澳', '台'], ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']],
    cityCodeIndex: [0, 0],
    /**
    ads:{
      id,
      adMsg
    }
    */
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
    console.log('calling owner')
  },

  bindPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      locationIndex: e.detail.value
    })
  },
  bindMultiPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      cityCodeIndex: e.detail.value
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
    console.log("page load ready.")
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