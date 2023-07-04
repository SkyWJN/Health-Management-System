Component({
	data: {
		selected: 0,
		list: [{
				pagePath: "/pages/index/index",
				text: "运动资讯",
        icon: 'newspaper-o',
			},
			{
				pagePath: "/pages/food/food",
				text: "饮食推荐",
				icon: 'good-job-o',
			},
			{
				pagePath: "/pages/sport/sport",
				text: "运动统计",
				icon: 'fire-o',
			},
			{
				pagePath: "/pages/user/user",
				text: "个人中心",
				icon: 'contact',
			},
		]
	},
	methods: {
		onChange(e) {
			console.log(e, 'e')
			this.setData({
				active: e.detail
			});
			wx.switchTab({
				url: this.data.list[e.detail].pagePath
			});
		},
		init() {
			const page = getCurrentPages().pop();
			this.setData({
				active: this.data.list.findIndex(item => item.pagePath === `/${page.route}`)
			});
		}
	}
})