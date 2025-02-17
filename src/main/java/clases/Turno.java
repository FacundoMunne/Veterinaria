package clases;

import java.time.LocalDateTime;

public class Turno {
	
		private Mascota mascota;
		private Profesional profesional;
		private LocalDateTime fechaHora;
		private String estado;
		
		
		
		public Turno(Mascota mascota, Profesional profesional, LocalDateTime fechaHora, String estado) {
			this.mascota = mascota;
			this.profesional = profesional;
			this.fechaHora = fechaHora;
			this.estado= estado;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
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
