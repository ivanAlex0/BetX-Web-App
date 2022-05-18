import {useEffect, useState} from "react";
import {get} from "../utils";
import {useNavigate} from "react-router-dom";

function CustomerHome() {
    const navigate = useNavigate()
    const [tokens, setTokens] = useState(get("tokens"))

    useEffect(() => {
        if (tokens === null)
            navigate("/")
    }, [])

    return (
        <div>
            <h1>
                Customer Home
            </h1>
        </div>
    );
}

export default CustomerHome;