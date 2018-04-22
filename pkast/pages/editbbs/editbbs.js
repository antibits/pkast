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
  
  radioChange: function (e) {
    var selectType = e.detail.value;

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
    wx.showToast({
      title: '发布成功',
      icon: 'success'
    })
    setTimeout(function(){
      wx.navigateBack({
        delta:1
      })
    }, 1500);
  }
}

Page(editbbsData)