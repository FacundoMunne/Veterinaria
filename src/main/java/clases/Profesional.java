package clases;

public class Profesional {
	private int idProfesional;
	private String dni;
	private String nombre;
    private String especialidad;
    private String telefono;
    private String email;
    
    public Profesional(int id,String dni, String nombre, String especialidad, String telefono, String email) {
        this.idProfesional = id;
        this.dni=dni;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
    }
    
	public int getIdProfesional() {
		return idProfesional;
	}
	public void setIdProfesional(int idProfesional) {
		this.idProfesional = idProfesional;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

    
}
