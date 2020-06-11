import React from "react";

class ExcursieForm extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            id: "",
            numeCompTrans: "",
            obiectiv: "",
            oraPlecarii: "",
            pret: "",
            nrLocuri: ""
        };
    }

    handleIdChange = (event) => {
        this.setState({id: event.target.value});
    }

    handleNumeCompTransChange = (event) => {
        this.setState({numeCompTrans: event.target.value});
    }

    handleObiectivChange = (event) => {
        this.setState({obiectiv: event.target.value});
    }

    handleOraPlecariiChange = (event) => {
        this.setState({oraPlecarii: event.target.value});
    }

    handlePretChange = (event) => {
        this.setState({pret: event.target.value});
    }

    handleNrLocuriChange = (event) => {
        this.setState({nrLocuri: event.target.value});
    }

    handleSubmit = (event) => {
        event.preventDefault();
        var excursie = {
            id: this.state.id,
            numeCompTrans: this.state.numeCompTrans,
            obiectiv: this.state.obiectiv,
            oraPlecarii: this.state.oraPlecarii,
            pret: this.state.pret,
            nrLocuri: this.state.nrLocuri
        }
        console.log("Am trimis o excursie");
        console.log(excursie);
        this.props.adaugare(excursie);
    }

    render() {
        const {id,
            numeCompTrans,
            obiectiv,
            oraPlecarii,
            pret,
            nrLocuri} = this.state;
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Id:
                    <input type="text" value={id} onChange={this.handleIdChange} />
                </label><br/>
                <label>
                    Companie:
                    <input type="text" value={numeCompTrans} onChange={this.handleNumeCompTransChange} />
                </label><br/>
                <label>
                    Obiectiv:
                    <input type="text" value={obiectiv} onChange={this.handleObiectivChange} />
                </label><br/>
                <label>
                    Ora Plecarii:
                    <input type="text" value={oraPlecarii} onChange={this.handleOraPlecariiChange} />
                </label><br/>
                <label>
                    Pret:
                    <input type="text" value={pret} onChange={this.handlePretChange} />
                </label><br/>
                <label>
                    Numar locuri:
                    <input type="text" value={nrLocuri} onChange={this.handleNrLocuriChange} />
                </label><br/>
                <input type="submit" value="Submit" />
            </form>
        );
    }
}

export default ExcursieForm;