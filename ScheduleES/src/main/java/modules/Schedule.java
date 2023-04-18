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

}




