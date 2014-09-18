function getInteger(s) {
    var i = parseInt(s);
    if (isNaN(i)) {
	i = 0;
    }
    return i;
}
function getFloat(s) {
    var f = parseFloat(s);
    if (isNaN(f)) {
	f = 0.0;
    }
    return f
}
function getFloatByPrompt(prompt) {
    var result = 0.0;
    do {
	var s = window.prompt(prompt);
	result = getFloat(s);
    } while (result <= 0);
    return result;
}
    
function FuelCalculator(entryCountSpanId, avgFuelEconomySpanId, avgFuelCostSpanId, lineItemContainerId) {
    this.entryCountSpan = document.getElementById(entryCountSpanId);
    this.avgFuelEconomySpan = document.getElementById(avgFuelEconomySpanId);
    this.avgFuelCostSpan = document.getElementById(avgFuelCostSpanId);
    this.lineItemContainer = document.getElementById(lineItemContainerId);
    this.entryCount = getInteger(this.entryCountSpan.innerHTML);
    this.avgFuel = Math.floor(getFloat(this.avgFuelEconomySpan.innerHTML) * 100);
    this.avgCostCents = Math.floor(getFloat(this.avgFuelCostSpan.innerHTML) * 100);
    this.totalFuel = this.avgFuel * this.entryCount;
    this.totalCostCents = this.avgCostCents * this.entryCount;
}
FuelCalculator.prototype.getEntries = function() {
    do {
	this.getEntry();
    } while (window.confirm('Add another entry?'));
}
FuelCalculator.prototype.getEntry = function() {
    var totalFuel = Math.floor(getFloatByPrompt('Enter total fuel (gallons)') * 100);
    var totalCost = Math.floor(getFloatByPrompt('Enter total cost (dollars)') * 100);
    var totalDist = Math.floor(getFloatByPrompt('Enter total distance (miles)') * 100);
    var now = new Date();
    this.totalCostCents += totalCost;
    var fuelEconomy = Math.floor(totalDist / totalFuel) * 100;
    this.totalFuel += fuelEconomy;
    this.lineItemContainer.innerHTML += '<tr><td>' + now.toString() + '</td><td>' + totalDist / 100 + '</td><td>' +
	totalFuel / 100 + '</td><td>' + fuelEconomy / 100 + '</td></tr>';
    this.entryCount++;
    this.avgCostCents = Math.floor(this.totalCostCents / this.entryCount);
    this.avgFuel = Math.floor(this.totalFuel / this.entryCount);
    this.entryCountSpan.innerHTML = this.entryCount;
    this.avgFuelEconomySpan.innerHTML = this.avgFuel / 100;
    this.avgFuelCostSpan.innerHTML = this.avgCostCents / 100;
}
