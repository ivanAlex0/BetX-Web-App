import {useEffect, useState} from "react";
import {get} from "../utils";
import {useNavigate} from "react-router-dom";
import axios from "axios";

function AdminHome() {
    const navigate = useNavigate()
    const [tokens, setTokens] = useState(get("tokens"))
    const [admin, setAdmin] = useState(get("admin-info"))
    const [odds, setOdds] = useState()

    useEffect(() => {
        if (tokens === null)
            navigate('/')
        if(admin === null)
            navigate('/admin/login')

        fetchOdds()
            .then(odds => {
                setOdds(odds)
            })
            .catch(error => console.log(error.response.data.message))
    }, [])

    function changeWinner(oddId, winner) {
        sendChangeWinner(oddId, winner, tokens)
            .then(response => {
                console.log(response)
                window.location.reload(false)
            })
            .catch(error => console.error(error))
    }


    return (
        <div>
            Admin Home

            {odds?.map(odd => {
                let winner;
                if (odd.winner === null)
                    winner = "NULL";
                else if (odd.winner === true)
                    winner = "TRUE"
                else winner = "FALSE";
                return <div key={odd.id}>
                    <h4>Id: {odd.id}
                        <br/>
                        {odd.oddType} -- {odd.odd}
                        <br/>
                        Winner: {winner}
                        <br/>
                        <button onClick={() => changeWinner(odd.id, true)}>Winner</button>
                        ....
                        <button onClick={() => changeWinner(odd.id, false)}>Not winner</button>
                    </h4>
                </div>
            })}
        </div>
    );
}

const path = 'http://localhost:8080/';

async function fetchOdds() {
    const response = await axios(
        {
            method: 'GET',
            url: path + "admin/findAllOdds",
        });
    return await response.data;
}

async function sendChangeWinner(oddId, winner, tokens) {
    const response = await axios(
        {
            method: 'POST',
            url: path + "admin/changeOddStatus",
            headers: {
                Authorization: "Bearer " + tokens.accessToken
            },
            params: {
                oddId: oddId,
                status: winner
            }
        });
    return await response.data;
}

export default AdminHome;