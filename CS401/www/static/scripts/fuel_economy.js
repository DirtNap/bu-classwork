function Menu(controlButtonId, titlePanel, totalPanel, inputPanel, alternateButtonText) {
    this.controlButton = document.getElementById(controlButtonId);
    this.controlPanels = [];
    this.controlPanels['title'] = document.getElementById(titlePanel);
    this.controlPanels['total'] = document.getElementById(totalPanel);
    this.controlPanels['input'] = document.getElementById(inputPanel);
    this.primaryButtonText = this.controlButton.value;
    this.alternateButtonText = alternateButtonText;
}
Menu.prototype.isActive = function() {
    return (this.controlButton.value != this.primaryButtonText);
}
    
Menu.prototype.toggle = function() {
    var targetPanel;
    if (this.isActive()) {
	this.controlButton.value = this.primaryButtonText;
	targetPanel = 'total';
    } else {
	    this.controlButton.value = this.alternateButtonText;
	    targetPanel = 'input';
	   }
    for (panel in this.controlPanels) {
	this.controlPanels[panel].style.display = 
	    (panel == targetPanel)?'block':'none';
    }
}
function calculatePrices(totalEntries, avgPrice, avgFuel, newPrice, newFuel, newMiles, lineItems, menu) {
    if (menu.isActive()) {
	txtFuel = document.getElementById(newFuel);
	txtCost = document.getElementById(newPrice);
	txtMile = document.getElementById(newMiles);
	var fuel = parseFloat(txtFuel.value);
	var cost = parseFloat(txtCost.value);
	var mile = parseFloat(txtMile.value);
	txtFuel.value = txtCost.value = txtMile.value = '';
	if (isNaN(fuel) || isNaN(cost) || isNaN(mile) ||
	   fuel < 0 || cost < 0 || mile < 0) {
	    window.alert('Invalid Entry!');
	    return false;
	}
	var now = new Date();
	var sEntryCount = document.getElementById(totalEntries);
	var sAvgPrice = document.getElementById(avgPrice);
	var sAvgFuel = document.getElementById(avgFuel);
	var tLineItems = document.getElementById(lineItems);
	var iCount = parseInt(sEntryCount.innerHTML);
	var fAvgPrice = parseFloat(sAvgPrice.innerHTML);
	var fAvgFuel = parseFloat(sAvgFuel.innerHTML);
	var iFuel = Math.floor((mile * 100) / fuel);
	var iTotalPrice = Math.floor(fAvgPrice * 100 * iCount);
	var iTotalFuel = Math.floor(fAvgFuel * 100 * iCount);
	++iCount;
	iTotalPrice += Math.floor(cost * 100);
	iTotalFuel += iFuel;
	fAvgPrice = Math.floor(iTotalPrice / iCount) / 100;
	fAvgFuel = Math.floor(iTotalFuel / iCount) / 100;
	sEntryCount.innerHTML = iCount;
	sAvgPrice.innerHTML = fAvgPrice;
	sAvgFuel.innerHTML = fAvgFuel;
	tLineItems.innerHTML += '<tr><td>' + now.toString() + '</td><td>' + mile + '</td><td>' +
	    fuel + '</td><td>' + (iFuel / 100) + '</td></tr>';
	if (window.confirm('Enter more fuelings?')) {
	    menu.toggle();
	}
    }
    menu.toggle();
}
