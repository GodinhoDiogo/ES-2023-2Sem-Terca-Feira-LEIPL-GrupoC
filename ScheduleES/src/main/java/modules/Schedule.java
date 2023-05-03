package modules;
import java.util.ArrayList;
import java.util.List;

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



	public void setCurso(String curso) {
		this.curso = curso;
	}



	public String getUnidadeCurricular() {
		return unidadeCurricular;
	}



	public void setUnidadeCurricular(String unidadeCurricular) {
		this.unidadeCurricular = unidadeCurricular;
	}



	public String getTurno() {
		return turno;
	}



	public void setTurno(String turno) {
		this.turno = turno;
	}



	public String getTurma() {
		return turma;
	}



	public void setTurma(String turma) {
		this.turma = turma;
	}



	public String getInscritosNoTurno() {
		return inscritosNoTurno;
	}



	public void setInscritosNoTurno(String inscritosNoTurno) {
		this.inscritosNoTurno = inscritosNoTurno;
	}



	public String getDiaSemana() {
		return diaSemana;
	}



	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}



	public String getHorarioInicioAula() {
		return horarioInicioAula;
	}



	public void setHorarioInicioAula(String horarioInicioAula) {
		this.horarioInicioAula = horarioInicioAula;
	}



	public String getHorarioFimAula() {
		return horarioFimAula;
	}



	public void setHorarioFimAula(String horarioFimAula) {
		this.horarioFimAula = horarioFimAula;
	}



	public String getDataAula() {
		return dataAula;
	}



	public void setDataAula(String dataAula) {
		this.dataAula = dataAula;
	}



	public String getSalaAtribuida() {
		return salaAtribuida;
	}



	public void setSalaAtribuida(String salaAtribuida) {
		this.salaAtribuida = salaAtribuida;
	}



	public String getLotacaoSala() {
		return lotacaoSala;
	}



	public void setLotacaoSala(String lotacaoSala) {
		this.lotacaoSala = lotacaoSala;
	}



	// Method to convert object to JSON
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	public boolean isEmpty() {
		return curso == null && unidadeCurricular == null && turno == null && turma == null && inscritosNoTurno == null && diaSemana == null && horarioInicioAula == null && horarioFimAula == null && dataAula == null && salaAtribuida == null && lotacaoSala == null;
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




