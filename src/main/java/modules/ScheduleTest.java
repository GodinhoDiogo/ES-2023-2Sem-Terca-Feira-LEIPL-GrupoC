package modules;
import com.google.gson.Gson;

/**

Represents a single schedule for a course
*/
public class ScheduleTest {

/**

The course name
*/
private String curso;
/**

The name of the academic unit
*/
private String unidadeCurricular;
/**

The shift of the schedule (morning, afternoon, night)
*/
private String turno;
/**

The group of the course
*/
private String turma;
/**

The number of students registered for the shift
*/
private String inscritosNoTurno;
/**

The day of the week of the schedule
*/
private String diaSemana;
/**

The start time of the class
*/
private String horarioInicioAula;
/**

The end time of the class
*/
private String horarioFimAula;
/**

The date of the class
*/
private String dataAula;
/**

The assigned room for the class
*/
private String salaAtribuida;
/**

The room's capacity
*/
private String lotacaoSala;
/**

Constructor for the Schedule class
@param curso The name of the course
@param unidadeCurricular The name of the academic unit
@param turno The shift of the schedule (morning, afternoon, night)
@param turma The group of the course
@param inscritosNoTurno The number of students registered for the shift
@param diaSemana The day of the week of the schedule
@param horarioInicioAula The start time of the class
@param horarioFimAula The end time of the class
@param dataAula The date of the class
@param salaAtribuida The assigned room for the class
@param lotacaoSala The room's capacity
*/
public ScheduleTest(String curso, String unidadeCurricular, String turno, String turma, String inscritosNoTurno,
String diaSemana, String horarioInicioAula, String horarioFimAula, String dataAula, String salaAtribuida,
String lotacaoSala) {
this.curso = curso;
this.unidadeCurricular = unidadeCurricular;
this.turno = turno;
this.turma = turma;
this.inscritosNoTurno = inscritosNoTurno;
this.diaSemana = diaSemana;
this.horarioInicioAula = horarioInicioAula;
this.horarioFimAula = horarioFimAula;
this.dataAula = dataAula;
this.salaAtribuida = salaAtribuida;
this.lotacaoSala = lotacaoSala;
}
/**

Empty constructor for the Schedule class
*/
public ScheduleTest() {}
/**

Getter for the course name
@return The name of the course
*/
public String getCurso() {
return curso;
}
/**

Getter for the name of the academic unit
@return The name of the academic unit
*/
public String getUnidadeCurricular() {
return unidadeCurricular;
}
/**

Getter for the shift of the schedule
@return The shift of the schedule
*/
public String getTurno() {
return turno;
}
/**
 */
/**

Returns the name of the class group to which this schedule belongs.
@return The class group name.
*/
public String getTurma() {
return turma;
}


/**

Get the number of enrolled students in the class's shift.
@return a String representing the number of enrolled students in the class's shift.
*/
public String getInscritosNoTurno() {
return inscritosNoTurno;
}
/**

Get the end time of the class.
@return a String representing the end time of the class.
*/
public String getHorarioFimAula() {
return horarioFimAula;
}
/**

Get the day of the week of the class.
@return a String representing the day of the week of the class.
*/
public String getDiaSemana() {
return diaSemana;
}
/**

Get the start time of the class.
@return a String representing the start time of the class.
*/
public String getHorarioInicioAula() {
return horarioInicioAula;
}
/**

Sets the course of the class.
@param curso the name of the course.
*/
public void setCurso(String curso) {
this.curso = curso;
}
/**

Sets the name of the academic unit.
@param unidadeCurricular the name of the academic unit.
*/
public void setUnidadeCurricular(String unidadeCurricular) {
this.unidadeCurricular = unidadeCurricular;
}
/**

Sets the shift of the class.
@param turno the shift of the class.
*/
public void setTurno(String turno) {
this.turno = turno;
}
/**

Sets the class group.
@param turma the class group.
*/
public void setTurma(String turma) {
this.turma = turma;
}
/**

Sets the number of enrolled students in the class shift.
@param inscritosNoTurno the number of enrolled students in the class shift.
*/
public void setInscritosNoTurno(String inscritosNoTurno) {
this.inscritosNoTurno = inscritosNoTurno;
}
/**

Sets the day of the week for the class.
@param diaSemana the day of the week for the class.
*/
public void setDiaSemana(String diaSemana) {
this.diaSemana = diaSemana;
}
/**

Sets the start time for the class.
@param horarioInicioAula the start time for the class.
*/
public void setHorarioInicioAula(String horarioInicioAula) {
this.horarioInicioAula = horarioInicioAula;
}
/**

Sets the end time for the class.
@param horarioFimAula the end time for the class.
*/
public void setHorarioFimAula(String horarioFimAula) {
this.horarioFimAula = horarioFimAula;
}
/**

Sets the date for the class.
@param dataAula the date for the class.
*/
public void setDataAula(String dataAula) {
this.dataAula = dataAula;
}
/**

Sets the assigned classroom for the class.
@param salaAtribuida the assigned classroom for the class.
*/
public void setSalaAtribuida(String salaAtribuida) {
this.salaAtribuida = salaAtribuida;
}
/**

Sets the capacity of the assigned classroom for the class.
@param lotacaoSala the capacity of the assigned classroom for the class.
*/
public void setLotacaoSala(String lotacaoSala) {
this.lotacaoSala = lotacaoSala;
}
/**

This method returns the date of the class.
@return a string representing the date of the class
*/
public String getDataAula() {
return dataAula;
}
/**

This method returns the room assigned to the class.
@return a string representing the room assigned to the class
*/
public String getSalaAtribuida() {
return salaAtribuida;
}
/**

This method returns the capacity of the room assigned to the class.
@return a string representing the capacity of the room assigned to the class
*/
public String getLotacaoSala() {
return lotacaoSala;
}
/**

This method converts the object to JSON format.
@return a string in JSON format representing the object
*/
public String toJson() {
Gson gson = new Gson();
return gson.toJson(this);
}


/**

This method returns a string representation of the Schedule object.
@return A string containing the values of the object's properties.
*/
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