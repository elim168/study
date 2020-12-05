package com.elim.study.spark.sql.test

case class PersonScala(_name: String, _age: Int, _sex: String) {

  var name = _name
  var age = _age
  var sex = _sex

  override def toString = s"PersonScala(name=$name, age=$age, sex=$sex)"
}
