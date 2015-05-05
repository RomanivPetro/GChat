<?php
class apitest extends apiBaseClass {
	

    //http://gchat.esy.es/api/?apitest.helloAPI={}
    function helloAPI() {
        $retJSON = $this->createDefaultJson();
        $retJSON->withoutParams = 'It\'s method called without parameters';
        return $retJSON;
    }

    //http://gchat.esy.es/api/?apitest.helloAPIWithParams={"TestParamOne":"Text of first parameter"}
    function helloAPIWithParams($apiMethodParams) {
        $retJSON = $this->createDefaultJson();
        if (isset($apiMethodParams->TestParamOne)){
            //Все ок параметры верные, их и вернем
            $retJSON->retParameter=$apiMethodParams->TestParamOne;
        }else{
            $retJSON->errorno=  APIConstants::$ERROR_PARAMS;
        }
        return $retJSON;
    }
    
    //http://gchat.esy.es/api/?apitest.helloAPIResponseBinary={"responseBinary":1}
    function helloAPIResponseBinary($apiMethodParams){
        header('Content-type: image/png');
        echo file_get_contents("http://habrahabr.ru/i/error-404-monster.jpg");
    }
	 //http://gchat.esy.es/api/?apitest.ReadFromDB={}
	function ReadFromDB(){	
	    $retJSON = $this->createDefaultJson();			
		$query = "SELECT name, last_name FROM users";			
		$retJSON = $this->dbExecutor($query);
		return $retJSON;	
	}
	
	//http://gchat.esy.es/api/?apitest.WriteToDB={}
	function WriteToDB(){	
	    $retJSON = $this->createDefaultJson();			
		$query = "INSERT INTO users ( id,user_id,name,last_name) VALUES (5,'id845685','Pavlo','Magera')";	
		$retJSON = $this->dbWriter($query);
		return $retJSON;	
	}
}

?>

