package clases;

import java.time.LocalDateTime;

public class Turno {
	
		private Mascota mascota;
		private Profesional profesional;
		private LocalDateTime fechaHora;
		
		
		
		public Turno(Mascota mascota, Profesional profesional, LocalDateTime fechaHora) {
			this.mascota = mascota;
			this.profesional = profesional;
			this.fechaHora = fechaHora;
		}
		public Turno() {
			// TODO Auto-generated constructor stub
		}
		public Mascota getMascota() {
			return mascota;
		}
		public void setMascota(Mascota mascota) {
			this.mascota = mascota;
		}
		public Profesional getProfesional() {
			return profesional;
		}
		public void setProfesional(Profesional profesional) {
			this.profesional = profesional;
		}
		public LocalDateTime getFechaHora() {
			return fechaHora;
		}
		public void setFechaHora(LocalDateTime fechaHora) {
			this.fechaHora = fechaHora;
		}
		
		
		

}
