<?php
class apiBaseClass {
    
    public $mySQLWorker=null;//Одиночка для работы с базой
    
    //Конструктор с возможными параметрами
    function __construct($dbName=null,$dbHost=null,$dbUser=null,$dbPassword=null) {
        if (isset($dbName)){//Если имя базы передано то будет установленно соединение с базой
            $this->mySQLWorker = MySQLiWorker::getInstance($dbName,$dbHost,$dbUser,$dbPassword);
        }
    }
    
    function __destruct() {
        if (isset($this->mySQLWorker)){             //Если было установленно соединение с базой, 
            $this->mySQLWorker->closeConnection();  //то закрываем его когда наш класс больше не нужен
        }
    }
    
    //Создаем дефолтный JSON для ответов
    function createDefaultJson() {	
        $retObject = json_decode('{}');
        return $retObject;
    }
	
	function fillJSON(&$stmt) {
		$assocArr = array();
		while($row = mysqli_fetch_assoc($stmt)){
			$assocArr[] = $row;
		}
		return $assocArr;         
    }
	
	function dbExecutor($query){
		$this->mySQLWorker = MySQLiWorker::getInstance(
		APIConstants::$db_Name, 
		APIConstants::$db_Host,
		APIConstants::$db_User,
		APIConstants::$db_Password);
		
	    $connectLink = $this->mySQLWorker->connectLink;					
		$stmt = $connectLink->query($query);
		$res = $this->fillJSON($stmt);
		$stmt->close();
		return $res;		
	}
	
	function dbWriter($query){
		$this->mySQLWorker = MySQLiWorker::getInstance(
		APIConstants::$db_Name, 
		APIConstants::$db_Host,
		APIConstants::$db_User,
		APIConstants::$db_Password);
		$connectLink = $this->mySQLWorker->connectLink;	
		try{	    				
		$connectLink->query($query);
		}
		catch(Exception $ex){
			return false;
		}
		return true;
	}

/* 
    ///Заполняем JSON объект по ответу из MySQLiWorker
    function fillJSON(&$jsonObject, &$stmt, &$mySQLWorker) {		
        $row = array();
		$res = array();
		$count = 0;
        $mySQLWorker->stmt_bind_assoc($stmt, $row); 		
		while($stmt->fetch()){			
            foreach ($row as $key => $value) {
                $key = strtolower($key);
                $jsonObject->$key = $value;									
            }  					
		}    	   	
		$jsonObject = $res;      
    }
	*/
}

?>