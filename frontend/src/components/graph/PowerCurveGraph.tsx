import { arePropsEmpty, isPropsEmpty } from "@src/utils/props";
import { forwardRef, RefObject } from "react";
import { Scatter, Line, XAxis, YAxis, CartesianGrid, Tooltip, ComposedChart, Legend, ZAxis } from "recharts";
import styled from "styled-components";

/**
 * Style
 */
const Title = styled.div`
  font-size: ${({ theme }) => theme.font.size.large};
  font-weight: ${({ theme }) => theme.font.weight.bold};
`;

/**
 * Types
 */
type Point = {
  windSpeed: number;
  activePower: number;
};

export type PowerCurveGraphProps = {
  referencePowerCurve: Point[];
  powerCurveScatter: Point[];
  title: string;
  ref: RefObject<HTMLDivElement>;
};

/**
 *
 * @returns
 */

const PowerCurveGraph = forwardRef<HTMLDivElement, PowerCurveGraphProps>(
  ({ title = "Power curve", referencePowerCurve, powerCurveScatter }, ref) => {
    return (
      <>
        <Title>{title}</Title>
        <div ref={ref}>
          <ComposedChart width={850} height={400} margin={{ top: 20, right: 20, bottom: 20, left: 20 }}>
            <CartesianGrid />
            <XAxis
              type="number"
              dataKey="windSpeed"
              name="Wind Speed"
              unit="m/s"
              domain={[0, 20]}
              label={{
                value: `Wind power[m/s]`,
                style: { textAnchor: "middle" },
                position: "bottom",
              }}
            />
            <YAxis
              type="number"
              dataKey="activePower"
              name="Active Power"
              unit="W"
              domain={[-100, 4500]}
              label={{
                value: `Active power[kW]`,
                style: { textAnchor: "middle" },
                angle: -90,
                position: "left",
                offset: 15,
              }}
              ticks={[0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500]}
            />
            <Tooltip />
            <Legend layout="horizontal" verticalAlign="top" align="center" wrapperStyle={{ top: 0, left: 25 }} />
            <ZAxis range={[10]} />

            <Line
              name="Reference power curve"
              data={referencePowerCurve}
              stroke="red"
              dataKey="activePower"
              strokeWidth="2"
              dot={false}
            />
            <Scatter name="Scatter data" data={powerCurveScatter} fill="blue" />
          </ComposedChart>
        </div>
      </>
    );
  },
);

export default PowerCurveGraph;
