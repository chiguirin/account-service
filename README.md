# account-service
Este microservicio se encarga de la gestión de cuentas dentro del sistema. Su objetivo principal es crear y administrar cuentas bancarias asociadas a clientes, aplicando validaciones de negocio antes de guardar la información.

Además de exponer endpoints REST, controla reglas como la validación del estado del cliente, la prevención de cuentas duplicadas y la coherencia del estado de la cuenta. Varias validaciones se realizan a nivel de dominio y servicio, no únicamente en el controlador.

Está desarrollado con Spring Boot y funciona de forma independiente, con su propia configuración y base de datos, lo que permite un despliegue desacoplado del resto de componentes.
