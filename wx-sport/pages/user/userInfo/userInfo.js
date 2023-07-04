// pages/user/userInfo/userInfo.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: wx.getStorageSync('userInfo'),
    nickName:'',
    email:'',
    phoneNumber:'',
    sex: 1,
    address:'',
  },

  onClickIcon(){
    console.log('nickName',this.data.nickName);
  },

  onClick(event){
    // console.log('click c',event);
  },

  onChange(event){
    console.log('click r',event);
    let temp = event.detail;
    this.setData({
      sex: temp,
    })
  },

  updateInfo(){
    console.log('update');
    let form = {
      nickName: this.data.nickName,
      email: this.data.email,
      phoneNumber: this.data.phoneNumber,
      address: this.data.address,
      sex: this.data.sex,
      openId: wx.getStorageSync('openid'),
    };

    app.ajax('mini/update/info','POST',form).then((res)=>{
      console.log('res',res);
      if(res.flag){
        wx.showToast({
          title: res.message,
        });
        this.setData({
          'userInfo.nickName': form.nickName,
          'userInfo.email': form.email,
          'userInfo.phoneNumber': form.phoneNumber,
          'userInfo.address': form.address,
          'userInfo.sex': form.sex,
        })
        wx.setStorageSync('userInfo', this.data.userInfo);
        // console.log('date',this.data.userInfo);
        console.log('nickName',this.data.nickName);
        wx.navigateBack();
      }
    });
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      userInfo: wx.getStorageSync('userInfo'),
    });
    this.setData({
      nickName: this.data.userInfo.nickName,
      email: this.data.userInfo.email,
      phoneNumber: this.data.userInfo.phoneNumber,
      address: this.data.userInfo.address,
      sex: this.data.userInfo.sex,
    });
    console.log('sex',this.data.sex);
    // console.log('jiazai',this.data.userInfo.nickName);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})