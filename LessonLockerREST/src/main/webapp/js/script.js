
window.addEventListener('load', function() {
	console.log("Window loaded.");

	(() => {
		console.log("IIFE.");
	})();

});
