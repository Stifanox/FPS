import spark.Spark.*
import Level
import LevelItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.sql.DriverManager
import DataInfo
import javax.xml.crypto.Data

fun main(args: Array<String>)  {

    val megaLevel = mutableListOf<LevelItem>(LevelItem(id=1, x=1, z=0, type="wall"), LevelItem(id=1, x=1, z=0, type="wall"), LevelItem(id=2, x=2, z=0, type="wall"), LevelItem(id=3, x=3, z=0, type="wall"), LevelItem(id=4, x=4, z=0, type="wall"), LevelItem(id=5, x=5, z=0, type="wall"), LevelItem(id=13, x=3, z=1, type="enemy"), LevelItem(id=15, x=5, z=1, type="wall"), LevelItem(id=16, x=6, z=1, type="wall"), LevelItem(id=17, x=7, z=1, type="wall"), LevelItem(id=18, x=8, z=1, type="wall"), LevelItem(id=19, x=9, z=1, type="wall"), LevelItem(id=20, x=0, z=2, type="wall"), LevelItem(id=21, x=1, z=2, type="wall"), LevelItem(id=24, x=4, z=2, type="wall"), LevelItem(id=25, x=5, z=2, type="wall"), LevelItem(id=26, x=6, z=2, type="wall"), LevelItem(id=28, x=8, z=2, type="enemy"), LevelItem(id=29, x=9, z=2, type="wall"), LevelItem(id=31, x=1, z=3, type="wall"), LevelItem(id=32, x=2, z=3, type="light"), LevelItem(id=33, x=3, z=3, type="wall"), LevelItem(id=34, x=4, z=3, type="wall"), LevelItem(id=36, x=6, z=3, type="enemy"), LevelItem(id=37, x=7, z=3, type="light"), LevelItem(id=39, x=9, z=3, type="wall"), LevelItem(id=41, x=1, z=4, type="wall"), LevelItem(id=43, x=3, z=4, type="wall"), LevelItem(id=44, x=4, z=4, type="tresure"), LevelItem(id=45, x=5, z=4, type="tresure"), LevelItem(id=48, x=8, z=4, type="enemy"), LevelItem(id=49, x=9, z=4, type="wall"), LevelItem(id=51, x=1, z=5, type="wall"), LevelItem(id=53, x=3, z=5, type="wall"), LevelItem(id=54, x=4, z=5, type="wall"), LevelItem(id=55, x=5, z=5, type="wall"), LevelItem(id=56, x=6, z=5, type="enemy"), LevelItem(id=59, x=9, z=5, type="wall"), LevelItem(id=61, x=1, z=6, type="wall"), LevelItem(id=62, x=2, z=6, type="enemy"), LevelItem(id=67, x=7, z=6, type="wall"), LevelItem(id=68, x=8, z=6, type="wall"), LevelItem(id=69, x=9, z=6, type="wall"), LevelItem(id=70, x=0, z=7, type="wall"), LevelItem(id=72, x=2, z=7, type="light"), LevelItem(id=75, x=5, z=7, type="light"), LevelItem(id=77, x=7, z=7, type="wall"), LevelItem(id=80, x=0, z=8, type="wall"), LevelItem(id=83, x=3, z=8, type="enemy"), LevelItem(id=84, x=4, z=8, type="enemy"), LevelItem(id=87, x=7, z=8, type="wall"), LevelItem(id=90, x=0, z=9, type="wall"), LevelItem(id=91, x=1, z=9, type="wall"), LevelItem(id=92, x=2, z=9, type="wall"), LevelItem(id=93, x=3, z=9, type="wall"), LevelItem(id=94, x=4, z=9, type="wall"), LevelItem(id=95, x=5, z=9, type="wall"), LevelItem(id=96, x=6, z=9, type="wall"), LevelItem(id=97, x=7, z=9, type="wall"))
    var level: Level = Level(10,megaLevel)
    var list: MutableList<LevelItem>

    val type = object : TypeToken<MutableList<LevelItem>>() {}.type
    staticFileLocation("/public")
    port(5000)
    get("/"){req,res ->  }


    post("/add"){req,res ->
        list = Gson().fromJson(req.body(),type)
        level = Level(10,list)
        println(level)
        "Odsyłam"
    }

    post("/load"){req,res ->

        if(req.body().toString().contains("test")){
            res.status(200)
            res.header("Access-Control-Allow-Origin","*")
            Gson().toJson(level.list)
        }
        else{
            level.list = megaLevel
            res.status(200)
            res.header("Access-Control-Allow-Origin","*")
            Gson().toJson(level.list)
        }

    }

    post("/saveInfo"){req,res ->
        val gameEnd = Gson().fromJson(req.body(), DataInfo::class.java)
        //FPS to nazwa bazy danych zmienić hasło
       val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/FPS", "postgres", "vfr132GY")
        val state = conn.createStatement()
        state.execute("INSERT INTO score VALUES(${gameEnd.dlugosc}, ${gameEnd.enemyDefeted})")
        conn.close()
        ""
    }
}

