$(function() {
	$layout = $('.metro-layout');
	$container = $('.metro-layout .content');
	function changeLayoutMode(isHorizontal) {
		$('.items', $layout).removeAttr('style');
		if (isHorizontal) {
			$('.items', $layout).css({
				width : $('.items', $layout).outerWidth()
			}).isotope({
				itemSelector : '.box',
				layoutMode : 'masonryHorizontal',
				animationEngine : 'css'
			});
		} else {
			$('.items', $layout).css({
				width : 'auto'
			}).isotope({
				itemSelector : '.box',
				layoutMode : 'masonry',
				animationEngine : 'css'
			});
		}
	}
	changeLayoutMode($layout.hasClass('horizontal'));
	$('.next', $layout).click(function(ev) {
		ev.preventDefault();
		$container.stop().animate({
			scrollLeft : '+=' + ($('body').innerWidth() / 1.8)
		}, 400);
	});
	$('.prev', $layout).click(function(ev) {
		ev.preventDefault();
		$container.stop().animate({
			scrollLeft : '-=' + ($('body').innerWidth() / 1.8)
		}, 400);
	});
	$('.up', $layout).click(function(ev) {
		ev.preventDefault();
		$container.stop().animate({
			scrollTop : '-=' + ($('body').innerHeight() / 1.8)
		}, 400);
	});
	$('.down', $layout).click(function(ev) {
		ev.preventDefault();
		$container.stop().animate({
			scrollTop : '+=' + ($('body').innerHeight() / 1.8)
		}, 400);
	});
	$('.toggle-view', $layout).click(function(ev) {
		ev.preventDefault();
		$layout.toggleClass('horizontal vertical');
		changeLayoutMode($layout.hasClass('horizontal'));
		toggleSlideControls();
	});
	function toggleSlideControls() {
		var hasHScrollbar = $container.get(0).scrollWidth > $container
				.innerWidth();
		var hasVScrollbar = $container.get(0).scrollHeight > $container
				.innerHeight();
		if (hasHScrollbar)
			$('.prev,.next', $layout).show();
		else
			$('.prev,.next', $layout).hide();
		if (hasVScrollbar)
			$('.up,.down', $layout).show();
		else
			$('.up,.down', $layout).hide();
	}
	;
	toggleSlideControls();
	var resizeTimer;
	$(window).bind('resize', function() {
		clearTimeout(resizeTimer);
		resizeTimer = setTimeout(toggleSlideControls, 100);
	});
	$container.dragscrollable({
		dragSelector : '.items'
	});
})