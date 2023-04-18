import com.google.gson.Gson;

public class Schedule {
  private String curso;
  private String unidadeCurricular;
  private String turno;
  private String turma;
  private int inscritosNoTurno;
  private String horarioFimAula;
  private String diaSemana;
  private String horarioInicioAula;
  private String dataAula;
  private String salaAtribuida;
  private int lotacaoSala;
  
  // Constructor
  public Schedule(String curso, String unidadeCurricular,String turno,String turma, int inscritosNoTurno, String diaSemana,
      String horarioInicioAula,String horarioFimAula, String dataAula, String salaAtribuida, int lotacaoSala) {
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

  // Method to convert object to JSON
  public String toJson() {
	  Gson gson = new Gson();
    return gson.toJson(this);
  }


  
