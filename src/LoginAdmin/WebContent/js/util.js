function DisableCtrlKey(e)
{
	var code = (document.all) ? e.keyCode : e.which;

	// look for CTRL key press
	if (e.ctrlKey && parseInt(code)==86)
	{
		return false;
	}
	return true;
}
