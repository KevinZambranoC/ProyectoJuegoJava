# Proyecto: Juego del Ascensor 🚀🎯

## Descripción del Proyecto 📝
Este proyecto consiste en un juego desarrollado en Java con una Interfaz Gráfica de Usuario (GUI) que simula un personaje que se desplaza verticalmente en un ascensor, mientras dispara flechas para reventar globos sostenidos por lobos que caen desde la parte superior del escenario. Los globos tienen colores específicos que otorgan diferentes puntajes al reventarlos:

- 🟡 **Globo Amarillo**: 100 puntos
- 🟢 **Globo Verde**: 200 puntos
- 🌸 **Globo Rosado**: 300 puntos
- 🟠 **Globo Naranja (Bonus)**: 400 puntos, aparece cada 25 segundos

El objetivo del juego es acumular 2000 puntos en 2 minutos. Si el jugador logra el puntaje en el tiempo establecido, gana; de lo contrario, pierde. El tiempo se muestra en formato MM:SS y los puntos acumulados se actualizan en tiempo real en la pantalla.

## Características del Juego 🎮
- **Movimiento del Personaje**: Control vertical con las flechas de dirección para moverse dentro del ascensor.
- **Disparo de Flechas**: Presiona ENTER para disparar flechas y reventar los globos de los lobos.
- **Generación Aleatoria de Lobos**: Los lobos con globos aparecen aleatoriamente en tres columnas diferentes.
- **Bonus de Globo Naranja**: Cada 25 segundos aparece un lobo con un globo naranja que otorga un puntaje extra.
- **Control de Tiempo**: El juego comienza con un temporizador de 2 minutos (02:00) que cuenta regresivamente hasta 00:00.
- **Detección de Colisiones**: Las flechas impactan en los globos, eliminando al lobo y sumando puntos de acuerdo al color del globo.

## Instrucciones de Instalación 💻
1. Clona este repositorio:
   ```bash
   git@github.com:KevinZambranoC/ProyectoJuegoJava.git
