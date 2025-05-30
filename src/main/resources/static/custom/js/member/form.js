
	$(document).ready(function () {
	$('#saveBtn').on('click', function (e) {
		e.preventDefault(); // Evitar que el botón intente enviar antes de validar

		if (isValidateBirthDate()) {
			Swal.fire({
				icon: 'success',
				title: '¡Fecha válida!',
				text: 'El formulario se está enviando...',
				showConfirmButton: false,
				timer: 1500
			}).then(() => {
				$('#member-form').submit(); // Enviar después de mostrar mensaje
			});
		} else {
			Swal.fire({
				icon: 'error',
				title: 'Fecha inválida',
				text: 'Por favor ingrese una fecha válida en formato dd-mm-aaaa',
			});
		}
	});

	function isValidateBirthDate() {
	var dateStr = $('#dateOfBirth').val();

	// Validar patrón dd-mm-aaaa
	var datePattern = /^(0[1-9]|[12][0-9]|3[01])[-\/](0[1-9]|1[0-2])[-\/](\d{4})$/;
	if (!datePattern.test(dateStr)) return false;

	// Convertir a objeto de fecha real (reorganizar a mm-dd-aaaa para Date.parse)
	var parts = dateStr.split("-");
	var day = parseInt(parts[0], 10);
	var month = parseInt(parts[1], 10) - 1; // JS usa 0-11 para los meses
	var year = parseInt(parts[2], 10);
	var date = new Date(year, month, day);

	return (
	date.getFullYear() === year &&
	date.getMonth() === month &&
	date.getDate() === day
	);
}

	$('#gotoListBtn').on('click', function () {
	window.location = "/member/list";
});
});


