Feature: Product - Store

  Scenario: Realizar el login - Store

  Given estoy en la página de la tienda
  When doy click en la opción iniciar sesión
  And doy click en el link text cree una cuenta aquí
  And lleno los campos del formulario para el registro
  And doy click en el botón guardar
  Then debería visualizar mi usuario logeado en la pantalla