function toggleGallery() {
    var menuLabel = document.getElementById('viewMenuItem');
    var menuTarget = document.getElementById('gallery');
    var targetMode = menuLabel.innerHTML;
    switch(targetMode) {
    case 'View':
	menuLabel.innerHTML = 'Hide';
	menuTarget.style.display = 'block';
	break;
    case 'Hide':
	menuLabel.innerHTML = 'View';
	menuTarget.style.display = 'none';
	break;
    }
}