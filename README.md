# climaGalaxia.
> <b>Version: 0.0.1</b>



- Autor: Juan Manuel Fabbri
- email": juanmanuelfabbri@gmail.com



> <b> consideraciones : </b>
	-Los planetas inician su trayectoria posicionados sobre el eje X a la distancia correspondiende del Sol.
	-Se tomo en cuenta que el SOL esta situado en el punto x:0, y:0.
	-Cuando los planetas forman un triángulo que no contiene al Sol, el clima es Normal.
	-Se utilizó una precisión de dos) decimales para realizar los cálculos matemáticos, posicionales y de alineación.
	-El lapso de predicción es de 10 años, tomando como cantidad de días de todos los años de 365.
	-Los tipos de clima son Optimo, Sequia, Lluvia, Normal
	-Para Inicializar el sistema meteorológico se supuso que los planetas parten de un dia 0 en donde todos estaban alineados con el Sol.
	-EstadoGalaxia: "No iniciada"
	-El proyecto se encuentra alojado en GitHub https://github.com/juanmafabbri/ingreso.git


> <b> Endpoints: </b>
	-job Galaxia: http://35.198.15.17:8080/galaxias/iniciar 
	-Iniciar nuevamente la galaxia: http://35.198.15.17:8080/galaxias/reiniciar
	-Consultar clima de un dia: http://35.198.15.17:8080/galaxias/clima?dia={1|3650}
	-Consultar Periodos climáticos en la galaxia: http://35.198.15.17:8080/galaxias/periodos?tipo={Optimo|Sequia|Lluvia|Normal}
	-Consultar día con mas lluvia: http://35.198.15.17:8080/galaxias/maximoLluvia
	-Información del sistema: http://35.198.15.17:8080/galaxias/acercaDe






> <b> Tecnologías que fueron utilizadas </b>
-   Java 8
-   Maven
-   Spring boot
-   JUnit
-   Mockito
-   JPA
-   HyperSQL Database
-   Log4j
-   GitHub
-   APP Engine






						 					


