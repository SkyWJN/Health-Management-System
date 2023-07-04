// pages/index/details/details.js
const app = getApp();
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		// 存储运动咨询详情
		dataInfo: {},
		baseData: {},
		url: '',
		flag: false,
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad(options) {
		const {id,type} = options;
		app.ajax(`sport/${id}`, 'GET').then((res) => {
			let content = res.data.content;
			if(content){
				this.setData({
					dataInfo: app.towxml(content, 'markdown', { theme:'light' }),
					flag: true,
					baseData: res.data
				})	
			}
		});
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