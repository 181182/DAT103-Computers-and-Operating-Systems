LOGG=$1

read -p "Hva er hendelsen? >" hendelse

declare -i sum=0

for line in $(grep "$hendelse" $1 | cut -f 2);
do
sum=$(($sum + $line))
done

echo $sum
