zk.afterLoad("zul.db", function(){
	var _Cwgt = {};
	zk.override(zul.db.CalendarPop.prototype, _Cwgt, {
		redraw: function(out) {
			_Cwgt.redraw.apply(this, arguments);
			this._customChangeView();
		},
		open: function(silent) {
			_Cwgt.open.apply(this, arguments);
			this._customChangeView();
		},
		_customChangeView: function() {
			if(jq(this.parent.$n()).hasClass('datebox-year-only')) {
				var view = this._view;
				if(view == 'month' || view == 'day') {
					this._setView("year");
				}
			} else if(jq(this.parent.$n()).hasClass('datebox-month-only')) {
				if(this._view == 'day') {
					this._setView("month");
				}
			}
		},
		_chooseDate: function(target, val) {
			var view = this._view;
			if(jq(this.parent.$n()).hasClass('datebox-month-only') || jq(this.parent.$n()).hasClass('datebox-year-only')) {
				var date = this.getTime(),
					year = (view == 'decade' || view == 'year') ? val : date.getFullYear(),
					month = view == 'month' ? val : 0,
					date = 1;
				this._setTime(year, month, 1);
				if(view == 'decade') {
					this._setView("year");
				} else if(jq(this.parent.$n()).hasClass('datebox-month-only') && view == 'year') {
					this._setView("month");
				} else if(jq(this.parent.$n()).hasClass('datebox-month-only') && view == 'month' || jq(this.parent.$n()).hasClass('datebox-year-only') && view == 'year') {
					this.close();
					this.parent.updateChange_();
				}
			} else {
				_Cwgt._chooseDate.apply(this, arguments);
			}
		}
	});
});