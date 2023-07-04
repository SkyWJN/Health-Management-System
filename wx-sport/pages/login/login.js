const app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {

    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

    },

    /**
     * 一键登陆
     */
    login() {
      wx.login({
        success(res) {
          if (res.code) {
            //发起网络请求
            wx.request({
              url: 'http://192.168.30.1:8080/mini/login',
              data: {
                code: res.code
              },
              success: (res) => {
                const {
                  flag,
                  data,
                  message
                } = res.data;
                if (!flag) {
                  return wx.showToast({
                    title: message,
                    icon: 'error',
                    duration: 2000
                  })
                }
                wx.setStorageSync('token', `${data.tokenHead} ${data.token}`);
                wx.setStorageSync('userInfo', data.userInfo);
                wx.setStorageSync('openid', data.openid);
                // console.log('接口请求成功：-->',res);
                
                // 获取微信运动步数
                wx.getWeRunData({
                  success: (res) => {
                      // 拿 encryptedData 到开发者后台解密开放数据
                      const {
                          encryptedData,
                          iv
                      } = res;
                      wx.request({
                          url: 'http://192.168.30.1:8080/mini/wxrun',
                          method: 'POST',
                          data: {
                              encryptedData: encryptedData,
                              iv: iv,
                              sessionKey: data.sessionKey, // 微信小程序登陆时获取
                              openid: data.openid
                          },
                          header: {
                              'Authorization': wx.getStorageSync('token')
                          },
                          success: (result) => {
                              wx.setStorageSync('step', result.data.data);
                              wx.switchTab({
                                url: '/pages/index/index',
                            });
                          },
                      });
                  },
                  fail: (err) => {
                      wx.showToast({
                          title: '请关注“微信运动”公众号后重试',
                          icon: 'none',
                          duration: 3000
                      });
                  }
              });
  
              },
              fail: (err) => {
                console.log('接口请求失败： -->', err);
              }
            });
          } else {
            console.log('登录失败！' + res.errMsg);
          }
        }
      })
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
})