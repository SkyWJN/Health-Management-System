// app.js
App({
	onLaunch() {
		// 展示本地存储能力
		const logs = wx.getStorageSync('logs') || []
		logs.unshift(Date.now())
		wx.setStorageSync('logs', logs)

		// 登录
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
  towxml:require('/towxml/index'),
	ajax: (url, method, data) => {
		return new Promise((resolve, reject) => {
			wx.request({
				url: `http://192.168.30.1:8080/${url}`,
				method: method,
				data: data,
				header: {
					'Authorization': wx.getStorageSync('token')
				},
				success: (res) => {
                    if (res.data.flag) {
                        resolve(res.data);
                    } else {
                        wx.showToast({
                            title: res.data.message,
                            icon: 'error'
                          });
                        reject(res.data);
                    }
                },
                fail: (err) => {
                    wx.showToast({
                      title: '请求异常',
                      icon: 'error'
                    });
                    reject(err);
                }
			});
		});
	},
})