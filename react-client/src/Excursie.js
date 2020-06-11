import React from "react";

class ExcursieRow extends React.Component{
    handleClicked = (event) => {
        console.log("delete pentru" + this.props.excursie.id);
        this.props.deleteExcursie(this.props.excursie.id);
    }

    render() {
        return (
            <tr>
                <td>{this.props.excursie.id}</td>
                <td>{this.props.excursie.numeCompTrans}</td>
                <td>{this.props.excursie.obiectiv}</td>
                <td>{this.props.excursie.oraPlecarii}</td>
                <td>{this.props.excursie.pret}</td>
                <td>{this.props.excursie.nrLocuri}</td>
                <td><button  onClick={this.handleClicked}>Sterge</button></td>
            </tr>
        );
    }
}

class ExcursieTable extends React.Component{
    render() {
        var randuri = [];
        var stergere = this.props.deleteExcursie;
        this.props.excursii.forEach(function (excursie) {
            randuri.push(
                <ExcursieRow excursie = {excursie} key = {excursie.id} deleteExcursie = {stergere} />
            )
        });
        return (
            <div className="ExcursiiTable">
                <table className="center">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Companie</th>
                        <th>Obiectiv</th>
                        <th>Ora Plecarii</th>
                        <th>Pret</th>
                        <th>Numar Locuri</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>{randuri}</tbody>
                </table>

            </div>
        );
    }
}
export default ExcursieTable;