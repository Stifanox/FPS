class LevelInfo{
    constructor(id,x,z,type){
        this.id = id
        this.x = x
        this.z = z
        this.type = type
    }
}

const table = document.querySelector("table")
const btnAdd = document.querySelector(".add")
const btnLoad = document.querySelector(".load")
const btnLoadKox = document.querySelector(".loadKox")

let id = 0
const map =[]

for(let i=0; i<10; i++){
    const tr = document.createElement("tr")
    for(let j=0; j<10;j++){

        const td = document.createElement("td")
        
        tr.append(td)
        td.setAttribute("id", `${id}`)
        td.setAttribute("x", j)
        td.setAttribute("z", i)
        td.setAttribute("class", "void")
        id++

        td.addEventListener("click",function(){
            const type = document.querySelector("select").value
            this.setAttribute("class", type)
        })
    }
    table.append(tr)
}

let allTd = document.querySelectorAll("td")

btnAdd.addEventListener("click",function(){
    map.length = 0

    allTd = Array.from(allTd)

    allTd.forEach(element => {
        if(element.className != "void"){
            const id = element.getAttribute("id")
            const x = element.getAttribute("x")
            const z = element.getAttribute("z")
            const type = element.className
            map.push(new LevelInfo(id,x,z,type))
        }
    });
    fetch("/add", {
        method:"POST",
        headers: {"content-type": "application/json"},
        body:JSON.stringify(map)
    })
})

btnLoad.addEventListener("click",function(){
    fetch("/load",{
        body: JSON.stringify({info:"test"}),
        method:"POST"
    })
    .then(res => res.json())
    .then(res => {
        Array.from(allTd).forEach(el => el.setAttribute("class", "void"))
        res.forEach(el => {
            document.getElementById(el.id).setAttribute("class", el.type)
        })
    })
})

btnLoadKox.addEventListener("click",function (){
    fetch("/load", {
        body: JSON.stringify({info:"kox"}),
        method:"POST"
    })
        .then(res => res.json())
        .then(res => {
            Array.from(allTd).forEach(el => el.setAttribute("class", "void"))
            res.forEach(el => {
                document.getElementById(el.id).setAttribute("class", el.type)
            })
        })
})