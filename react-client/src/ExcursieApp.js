import React from "react";
import ExcursieForm from "./ExcursieForm";
import ExcursieTable from "./Excursie";
import "./ExcursieApp.css";
import {getExcursii, adaugareExcursie, deleteExcursie} from "./utils/rest-calls";

class ExcursieApp extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            excursii: [{
                "id":"vezi",
                "numeCompTransport":"ca",
                "obiectiv":"are",
                "oraPlecarii":"o",
                "pret":"eroare",
                "nrLocuri":"pagina"
            }],
            deleteExcursie:this.stergeExcursie.bind(this),
            adaugareExcursie:this.adaugaExcursie.bind(this),
        }
        console.log("ExcursieApp constructor")
    }

    adaugaExcursie(excursie){
        console.log("Sunt in adauare " + excursie);
        adaugareExcursie(excursie)
            .then(res => getExcursii())
            .then(excursii => this.setState({excursii}))
            .catch(error => console.log("eroare adaugare", error));
    }

    stergeExcursie(excursie){
        console.log("Sunt in stergere " + excursie);
        deleteExcursie(excursie)
            .then(res => getExcursii())
            .then(excursii => this.setState({excursii}))
            .catch(error => console.log("eroare stergere", error));
    }

    componentDidMount() {
        console.log("Sunt in componentDidMount")
        getExcursii().then(excursii =>{
            console.log(excursii);
            this.setState({excursii})
        });
    }

    render() {
        console.log(this.state.excursii);
        const {excursii} = this.state;
        return(
            <div className = "ExcursieApp">
                <h1>Excursii Management</h1>
                <ExcursieForm adaugare={this.state.adaugareExcursie}/>
                <br/>
                <br/>
                <ExcursieTable excursii={excursii} deleteExcursie={this.state.deleteExcursie}/>
            </div>
        );
    }
}

export default ExcursieApp;