CREATE TABLE Machines (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Axes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE MachineAxes (
    id SERIAL PRIMARY KEY,
    machine_id INT REFERENCES Machines(id),
    axis_id INT REFERENCES Axes(id),
    UNIQUE (machine_id, axis_id)
);

CREATE TABLE MachineValues (
    id SERIAL PRIMARY KEY,
    machine_id INT REFERENCES Machines(id),
    axis VARCHAR(50),
    timestamp TIMESTAMP NOT NULL,
    value FLOAT NOT NULL,
    UNIQUE (machine_id, axis, timestamp)
);
