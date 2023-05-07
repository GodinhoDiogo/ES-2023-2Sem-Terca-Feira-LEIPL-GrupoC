package modules;

import com.google.gson.Gson;


public class Schedule {
	private String curso;
	private String unidadeCurricular;
	private String turno;
	private String turma;
	private String inscritosNoTurno;
	private String diaSemana;
	private String horarioInicioAula;
	private String horarioFimAula;
	private String dataAula;
	private String salaAtribuida;
	private String lotacaoSala;

	// Constructor
	public Schedule(String curso, String unidadeCurricular,String turno,String turma, String inscritosNoTurno, String diaSemana,
			String horarioInicioAula,String horarioFimAula, String dataAula, String salaAtribuida, String lotacaoSala) {
		this.curso = curso;
		this.unidadeCurricular = unidadeCurricular;
		this.turno=turno;
		this.turma = turma;
		this.inscritosNoTurno = inscritosNoTurno;
		this.diaSemana = diaSemana;
		this.horarioInicioAula = horarioInicioAula;
		this.horarioFimAula= horarioFimAula;
		this.dataAula = dataAula;
		this.salaAtribuida = salaAtribuida;
		this.lotacaoSala = lotacaoSala;
	}
	public Schedule() {
		
	}
	public String getCurso() {
		return curso;
	}

	public String getUnidadeCurricular() {
		return unidadeCurricular;
	}

	public String getTurno() {
		return turno;
	}

	public String getTurma() {
		return turma;
	}

	public String getInscritosNoTurno() {
		return inscritosNoTurno;
	}

	public String getHorarioFimAula() {
		return horarioFimAula;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public String getHorarioInicioAula() {
		return horarioInicioAula;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
	public void setUnidadeCurricular(String unidadeCurricular) {
		this.unidadeCurricular = unidadeCurricular;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public void setTurma(String turma) {
		this.turma = turma;
	}
	public void setInscritosNoTurno(String inscritosNoTurno) {
		this.inscritosNoTurno = inscritosNoTurno;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	public void setHorarioInicioAula(String horarioInicioAula) {
		this.horarioInicioAula = horarioInicioAula;
	}
	public void setHorarioFimAula(String horarioFimAula) {
		this.horarioFimAula = horarioFimAula;
	}
	public void setDataAula(String dataAula) {
		this.dataAula = dataAula;
	}
	public void setSalaAtribuida(String salaAtribuida) {
		this.salaAtribuida = salaAtribuida;
	}
	public void setLotacaoSala(String lotacaoSala) {
		this.lotacaoSala = lotacaoSala;
	}
	
	/*
	public Date getDataAula() {
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        try {
	            Date time = sdf.parse(dataAula);
	            return time;
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		return null;
	}
*/
	public String getDataAula() {
		return dataAula;
	}
	public String getSalaAtribuida() {
		return salaAtribuida;
	}

	public String getLotacaoSala() {
		return lotacaoSala;
	}

	// Method to convert object to JSON
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	
	@Override
	public String toString() {
	    return "Schedule{" +
	            "curso='" + curso + '\'' +
	            ", unidadeCurricular='" + unidadeCurricular + '\'' +
	            ", turno='" + turno + '\'' +
	            ", turma='" + turma + '\'' +
	            ", inscritosNoTurno='" + inscritosNoTurno + '\'' +
	            ", diaSemana='" + diaSemana + '\'' +
	            ", horarioInicioAula='" + horarioInicioAula + '\'' +
	            ", horarioFimAula='" + horarioFimAula + '\'' +
	            ", dataAula='" + dataAula + '\'' +
	            ", salaAtribuida='" + salaAtribuida + '\'' +
	            ", lotacaoSala='" + lotacaoSala + '\'' +
	            '}';
	}


}




