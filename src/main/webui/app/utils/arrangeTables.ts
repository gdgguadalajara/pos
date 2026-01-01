import type { Floor, RestaurantTable } from "~/models";

export default function (floor: Floor, tables: RestaurantTable[], isVertical = false) {
    const matrix = Array.from({ length: floor.gridHeight ?? 0 },
        () => Array.from<RestaurantTable | undefined>({ length: floor.gridWidth ?? 0 }).fill(undefined))
    for (const table of tables) {
        matrix[table.posY as number]![table.posX as number] = table
    }
    const arrangeTables: (RestaurantTable | undefined)[] = []
    for (const col of isVertical ? rotateMatrix(matrix) : matrix)
        for (const cel of col)
            arrangeTables.push(cel)
    return arrangeTables
}

const rotateMatrix = (matrix: (RestaurantTable | undefined)[][]) => {
    const newList2d = Array.from({ length: matrix[0]!.length },
        () => Array.from<RestaurantTable | undefined>({ length: matrix.length }).fill(undefined));
    for (let i = 0; i < matrix.length; i++) {
        for (let j = 0; j < matrix[i]!.length; j++)
            newList2d[j]![matrix.length - 1 - i] = matrix[i]![j];
    }
    return newList2d;
}