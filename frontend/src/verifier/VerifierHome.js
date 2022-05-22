import {useEffect, useState} from "react";
import {get} from "../utils";
import {useNavigate} from "react-router-dom";
import axios from "axios";

function VerifierHome() {
    const navigate = useNavigate()
    const [tokens, setTokens] = useState(get("tokens"))
    const [verifier, setVerifier] = useState(get("verifier-info"))
    const [customers, setCustomers] = useState()

    useEffect(() => {
        if (tokens === null)
            navigate("/")
        if(verifier === null)
            navigate('/verifier/login')

        findAll()
            .then(customers => {
                setCustomers(customers)
            })
            .catch(error => console.error(error))
    }, [])

    function changeVerified(customerId, verified){
        sendChangeVerified(customerId, verified, tokens)
            .then(response => {
                window.location.reload(false)
                console.log(response)
            })
            .catch(error => console.error(error))
    }

    function changeSuspension(customerId, verified){
        sendChangeSuspension(customerId, verified, tokens)
            .then(response => {
                window.location.reload(false)
                console.log(response)
            })
            .catch(error => console.error(error))
    }

    return (
        <div>
            Verifier Home

            {customers?.map(cust => {
                return <div key={cust.customerId}>
                    <h1>
                        . {cust.customerId} {cust.name}
                    </h1>
                    <h2>
                        Verified: {cust.verified ? "Yes" : "No"} ....
                        <button onClick={() => changeVerified(cust.customerId, true)}>Verified</button> ....
                        <button onClick={() => changeVerified(cust.customerId, false)}>Not verified</button>
                    </h2>
                    <h2>
                        Suspended: {cust.suspended ? "Yes" : "No"}
                        <button onClick={() => changeSuspension(cust.customerId, true)}>Suspended</button> ....
                        <button onClick={() => changeSuspension(cust.customerId, false)}>Not suspended</button>

                    </h2>
                </div>
            })}
        </div>
    );
}

const path = 'http://localhost:8080/';

async function findAll(tokens) {
    const response = await axios(
        {
            method: 'GET',
            url: path + "verifier/findAll",
        });
    return await response.data;
}

async function sendChangeVerified(customerId, verified, tokens) {
    const response = await axios(
        {
            method: 'POST',
            url: path + "verifier/changeStatus",
            params: {
                customerId: customerId,
                verified: verified
            },
            headers: {
                Authorization: "Bearer " + tokens.accessToken
            }
        });
    return await response.data;
}

async function sendChangeSuspension(customerId, suspended, tokens) {
    const response = await axios(
        {
            method: 'POST',
            url: path + "verifier/changeSuspension",
            params: {
                customerId: customerId,
                suspended: suspended
            },
            headers: {
                Authorization: "Bearer " + tokens.accessToken
            }
        });
    return await response.data;
}

export default VerifierHome;